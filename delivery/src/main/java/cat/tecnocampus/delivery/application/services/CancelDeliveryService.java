package cat.tecnocampus.delivery.application.services;

import cat.tecnocampus.delivery.adapters.in.restAPI.DeliveryNotFoundException;
import cat.tecnocampus.delivery.application.ports.in.CancelDeliveryUseCase;
import cat.tecnocampus.delivery.application.ports.out.DeliveryRepository;
import cat.tecnocampus.delivery.model.Delivery;
import cat.tecnocampus.events.DeliveryCanceledEvent;
import cat.tecnocampus.events.DeliveryCannotCancelEvent;
import cat.tecnocampus.events.OrderCancelRequestEvent;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CancelDeliveryService implements CancelDeliveryUseCase {

    private final DeliveryRepository deliveryRepository;
    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange deliveryCanceledExchange;
    private final FanoutExchange deliveryCannotCancelExchange;

    public CancelDeliveryService(DeliveryRepository deliveryRepository, RabbitTemplate rabbitTemplate, FanoutExchange deliveryCanceledExchange, FanoutExchange deliveryCannotCancelExchange) {
        this.deliveryRepository = deliveryRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.deliveryCanceledExchange = deliveryCanceledExchange;
        this.deliveryCannotCancelExchange = deliveryCannotCancelExchange;
    }

    @Override
    public boolean cancelDelivery(long deliveryId) {
        var delivery = deliveryRepository.findById(deliveryId);
        if (delivery.isEmpty() || delivery.get().getStatus() != Delivery.Status.IN_PREPARATION) {
            return false;
        }
        deliveryRepository.updateDeliveryStatus(deliveryId, Delivery.Status.CANCELED);
        return true;
    }

    @RabbitListener(queues = "delivery.cancel.queue")
    public void handleOrderCancelRequestEvent(OrderCancelRequestEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println("\n📥 Received OrderCancelRequestEvent: " + event);
            var delivery = deliveryRepository.findByOrderId(event.orderId())
                    .orElseThrow(() -> new DeliveryNotFoundException(event.orderId()));

            if (cancelDelivery(delivery.getDeliveryId())) {
                DeliveryCanceledEvent deliveryCanceledEvent = new DeliveryCanceledEvent(delivery.getOrderId(), event.productId(), event.quantity(), event.price(), event.customerId());
                System.out.println("📤 Broadcasting DeliveryCanceledEvent");
                rabbitTemplate.convertAndSend(deliveryCanceledExchange.getName(), "", deliveryCanceledEvent);
            } else {
                System.out.println("\n📤 Broadcasting DeliveryCannotCancelEvent");
                DeliveryCannotCancelEvent deliveryCannotCancelEvent = new DeliveryCannotCancelEvent(event.orderId());
                rabbitTemplate.convertAndSend(deliveryCannotCancelExchange.getName(), "", deliveryCannotCancelEvent);
            }

            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.err.println("❌ Error processing event: " + e.getMessage());
            channel.basicNack(tag, false, true);
        }
    }
}
