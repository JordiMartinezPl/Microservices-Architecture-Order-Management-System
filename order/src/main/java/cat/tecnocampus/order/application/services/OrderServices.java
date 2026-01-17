package cat.tecnocampus.order.application.services;

import cat.tecnocampus.customer.application.ports.in.CustomerResponse;
import cat.tecnocampus.delivery.application.ports.in.DeliveryCommand;
import cat.tecnocampus.events.DeliveryCanceledEvent;
import cat.tecnocampus.events.DeliveryCannotCancelEvent;
import cat.tecnocampus.events.OrderCancelRequestEvent;
import cat.tecnocampus.order.adapters.in.restAPI.exceptions.*;
import cat.tecnocampus.order.application.ports.in.OrderCRUD;
import cat.tecnocampus.order.application.ports.in.OrderCommand;
import cat.tecnocampus.order.application.ports.out.OrderRepository;
import cat.tecnocampus.order.model.Order;
import cat.tecnocampus.orderhistory.model.OrderHistory;
import cat.tecnocampus.product.application.ports.in.ProductPriceResponse;
import cat.tecnocampus.product.application.ports.in.ProductResponse;
import com.rabbitmq.client.Channel;
import jakarta.transaction.Transactional;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class OrderServices implements OrderCRUD {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange deliveryCanceledExchange;
    private final FanoutExchange deliveryCannotCancelExchange;
    private final DirectExchange orderExcange;

    private final String productServiceUrl = "http://localhost:8001/product";
    private final String customerServiceUrl = "http://localhost:8002/customers";
    private final String deliveryServiceUrl = "http://localhost:8003/deliveries";
    private final String orderHistoryServiceUrl = "http://localhost:8004/orderHistory";

    public OrderServices(OrderRepository orderRepository, WebClient.Builder webClientBuilder, RabbitTemplate rabbitTemplate, DirectExchange orderExcange, FanoutExchange deliveryCanceledExchange, FanoutExchange deliveryCannotCancelExchange) {
        this.orderRepository = orderRepository;
        this.webClient = webClientBuilder.build();
        this.rabbitTemplate = rabbitTemplate;
        this.orderExcange = orderExcange;
        this.deliveryCanceledExchange = deliveryCanceledExchange;
        this.deliveryCannotCancelExchange = deliveryCannotCancelExchange;
    }

    @Override
    @Transactional
    public void createOrder(OrderCommand orderCommand) {
        Order order = null;
        Double amount = 0.0;
        try {
            //Step 1 create order
            order = new Order(orderCommand.productId(), orderCommand.quantity(), orderCommand.customerId());
            order = this.orderRepository.save(order);

            CustomerResponse customerResponse = getCustomer(order.getCustomerId());

            ProductResponse productResponse = getProductResponse(order.getProductId());

            postOrderHistory(order, customerResponse, productResponse);

            //Step2 Reserve stock

            reserveStock(order);

            //Step Withdraw credit

            amount = getTotalAmount(order);

            deductMoneyFromClient(order, amount);

            //STEP4 reserver deleivery

            reserveDelivery(order, customerResponse);

            //STEP 5 complete order
            completeOrder(order);

            //STEP 6 store order history

            updateCompletedOrderHistory(order);


        } catch (RuntimeException e) {
            // Compensation Actions
            if (e.getMessage().contains("Stock reservation failed")) { // Compensation for Step 2: Reserve Stock
                order.setStatus(Order.OrderStatus.REJECTED);
                updateRejectedOrderHistory(order);
                orderRepository.save(order);
                throw new StockReservationException("Stocker Reservation failed");

            } else if (e.getMessage().contains("Credit withdrawal failed")) {
                // Compensation for Step 6: Withdraw Credit

                order.setStatus(Order.OrderStatus.REJECTED);
                updateRejectedOrderHistory(order);
                orderRepository.save(order);
                restoreStockAmount(order);
                throw new NotEnoughCreditException();
            } else if (e.getMessage().contains("Delivery reservation failed")) {
                // Compensation for Step 7: Reserve Delivery

                restoreStockAmount(order);
                restoreCustomerCredit(order, amount);

                order.setStatus(Order.OrderStatus.REJECTED);
                updateRejectedOrderHistory(order);
                orderRepository.save(order);
                throw new DeliveryReserveException("The delivery failed in reservation");
            }
        }
    }

    private double getProductPrice(Long productId, int quantity) {

        double amount = webClient.get()
                .uri(productServiceUrl + "/{productId}/price/{quantity}", productId, quantity)
                .retrieve()
                .bodyToMono(Double.class)
                .block();

        return amount;
    }

    //----------------- CANCEL LOGIC -----------------//

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.getById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getStatus() == Order.OrderStatus.PENDING || order.getStatus() == Order.OrderStatus.FINISHED) {
            OrderCancelRequestEvent event = new OrderCancelRequestEvent(
                    order.getId(),
                    order.getProductId(),
                    order.getQuantity(),
                    getTotalAmount(order),
                    order.getCustomerId()
            );

            System.out.println("\n📤 Sending OrderCancelRequestEvent to DeliveryService: " + event);
            rabbitTemplate.convertAndSend(orderExcange.getName(), "order.cancel", event);
            System.out.println("\n✅ Order cancel request sent.");
        } else {
            throw new IllegalArgumentException("Order cannot be canceled because it is not in a cancellable state");
        }
    }


    @RabbitListener(queues = "order.delivery.canceled.queue")
    public void handleDeliveryCanceledEvent(DeliveryCanceledEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("\n📥 Received DeliveryCanceledEvent: " + event);
            Order order = orderRepository.getById(event.orderId())
                    .orElseThrow(() -> new OrderNotFoundException(event.orderId()));

            order.setStatus(Order.OrderStatus.CANCELLED);
            orderRepository.save(order);
            System.out.println("\n✅ Order status updated to CANCELED.");

            channel.basicAck(tag, false);

        } catch (Exception e) {
            System.err.println("❌ Error processing DeliveryCanceledEvent: " + e.getMessage());

            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queues = "order.delivery.cannot.cancel.queue")
    public void handleDeliveryCannotCancelEvent(DeliveryCannotCancelEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("\n📥 Received DeliveryCannotCancelEvent: " + event);
            Order order = orderRepository.getById(event.orderId())
                    .orElseThrow(() -> new OrderNotFoundException(event.orderId()));

            System.out.println("\n❌ Delivery cannot be canceled for order: " + order.getId());

            channel.basicAck(tag, false);

        } catch (Exception e) {
            System.err.println("❌ Error processing DeliveryCannotCancelEvent: " + e.getMessage());

            channel.basicNack(tag, false, true);
        }
    }


    //--------------- SAGA STEPS -----------------//


    public CustomerResponse getCustomer(Long customerId) {
        return webClient.get()
                .uri(customerServiceUrl + "/{id}", customerId)
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .onErrorMap(e -> new OrderNotFoundException("Failed to fetch customer details"))
                .block();
    }

    public ProductResponse getProductResponse(Long productId) {
        return webClient.get()
                .uri(productServiceUrl + "/{id}", productId)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .onErrorMap(e -> new OrderNotFoundException("Failed to fetch product details"))
                .block();
    }

    public void postOrderHistory(Order order, CustomerResponse customerResponse, ProductResponse productResponse) {

        webClient.post()
                .uri(orderHistoryServiceUrl)
                .bodyValue(new OrderHistory(order.getProductId(),
                        productResponse.name(),
                        productResponse.price(),
                        order.getQuantity(),
                        order.getCustomerId(),
                        customerResponse.name(),
                        customerResponse.email(),
                        customerResponse.address(),
                        LocalDateTime.now(), order.getId()
                ))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new ProcessFailedException("Order history storage failed")))
                .toBodilessEntity()
                .block();


    }


    public void reserveStock(Order order) {
        webClient.post()
                .uri(productServiceUrl + "/" + order.getProductId() + "/reserve/" + order.getQuantity())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("No response body")
                                .flatMap(errorBody ->
                                        Mono.error(new RuntimeException(
                                                "Stock reservation failed. HTTP Status: " + clientResponse.statusCode() +
                                                        ", Response: " + errorBody
                                        ))
                                )
                ).toBodilessEntity()
                .block();
    }


    public double getTotalAmount(Order order) {
        Double amount = webClient.get()
                .uri(productServiceUrl + "/{productId}/price/{quantity}", order.getProductId(), order.getQuantity())
                .retrieve()
                .bodyToMono(ProductPriceResponse.class)
                .map(ProductPriceResponse::getPrice)
                .onErrorMap(e -> new RuntimeException("Failed to retrieve product price", e))
                .block();
        if (amount == null) {
            throw new ProcessFailedException("Failed to retrieve product price");
        }
        return amount;
    }

    public void deductMoneyFromClient(Order order, Double amount) {
        webClient.post()
                .uri(customerServiceUrl + "/{id}/deduct/{amount}", order.getCustomerId(), amount)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Credit withdrawal failed")))
                .toBodilessEntity()
                .block();

    }


    public void reserveDelivery(Order order, CustomerResponse customerResponse) {
        webClient.post()
                .uri(deliveryServiceUrl)
                .bodyValue(new DeliveryCommand(order.getId(), customerResponse.address(), customerResponse.name(), customerResponse.email()))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Delivery reservation failed")))
                .toBodilessEntity()
                .block();
    }

    public void completeOrder(Order order) {
        order.setStatus(Order.OrderStatus.FINISHED);
        order = orderRepository.save(order);
    }


    public void updateCompletedOrderHistory(Order order) {
        webClient.put()
                .uri(orderHistoryServiceUrl + "/{orderId}/completed", order.getId())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("No response body")
                                .flatMap(errorBody ->
                                        Mono.error(new ProcessFailedException(
                                                "Order history failed. HTTP Status: " + clientResponse.statusCode() +
                                                        ", Response: " + errorBody
                                        ))
                                )
                ).toBodilessEntity()
                .block();
    }//

    public void updateRejectedOrderHistory(Order order) {
        webClient.put()
                .uri(orderHistoryServiceUrl + "/{orderId}/rejected", order.getId())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("No response body")
                                .flatMap(errorBody ->
                                        Mono.error(new ProcessFailedException(
                                                "Order history failed. HTTP Status: " + clientResponse.statusCode() +
                                                        ", Response: " + errorBody
                                        ))
                                )
                ).toBodilessEntity()
                .block();
    }

    //----------------- SAGA COMPENSATION -----------------//

    public void restoreStockAmount(Order order) {
        webClient.post()
                .uri(productServiceUrl + "/{productId}/restore/{quantity}", order.getProductId(), order.getQuantity())
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void restoreCustomerCredit(Order order, Double amount) {

        webClient.post()
                .uri(customerServiceUrl + "/{customerId}/restore/{amount}", order.getCustomerId(), amount)
                .retrieve()
                .toBodilessEntity()
                .block();
    }


}
