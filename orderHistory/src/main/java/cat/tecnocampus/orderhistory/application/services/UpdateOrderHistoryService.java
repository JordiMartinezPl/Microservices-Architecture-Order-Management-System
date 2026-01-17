package cat.tecnocampus.orderhistory.application.services;

import cat.tecnocampus.events.DeliveryCanceledEvent;
import cat.tecnocampus.orderhistory.adapters.in.restAPI.OrderHistoryNotFoundException;

import cat.tecnocampus.orderhistory.application.ports.in.UpdateOrderHistoryUseCase;
import cat.tecnocampus.orderhistory.application.ports.out.OrderHistoryRepository;
import cat.tecnocampus.orderhistory.model.OrderHistory;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Primary
public class UpdateOrderHistoryService implements UpdateOrderHistoryUseCase {

    private final OrderHistoryRepository orderHistoryRepository;

    public UpdateOrderHistoryService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    public void updateOrderHistoryRejected(long orderId, LocalDateTime rejectedDate) {
        OrderHistory orderHistory = orderHistoryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderHistoryNotFoundException(" OrderHistory with order id: " + orderId + " not found"));
        orderHistory.setFinalizationDate(rejectedDate);
        orderHistory.setOrderHistoryStatus(OrderHistory.OrderHistoryStatus.REJECTED);
        orderHistoryRepository.save(orderHistory);
    }

    @Override
    public void updateOrderHistoryCompleted(long orderId, LocalDateTime completedDate) {
        OrderHistory orderHistory = orderHistoryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderHistoryNotFoundException(" OrderHistory with order id: " + orderId + " not found"));
        orderHistory.setFinalizationDate(completedDate);
        orderHistory.setOrderHistoryStatus(OrderHistory.OrderHistoryStatus.FINISHED);
        orderHistoryRepository.save(orderHistory);
    }

    public void updateOrderHistoryCancelled(long orderId, LocalDateTime cancelledDate) {
        OrderHistory orderHistory = orderHistoryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderHistoryNotFoundException(" OrderHistory with order id: " + orderId + " not found"));
        orderHistory.setCancellationDate(cancelledDate);
        orderHistory.setOrderHistoryStatus(OrderHistory.OrderHistoryStatus.CANCELLED);
        orderHistoryRepository.save(orderHistory);
    }

    @RabbitListener(queues = "orderhistory.delivery.canceled.queue")
    public void handleOrderCanceledEvent(DeliveryCanceledEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            updateOrderHistoryCancelled(event.orderId(), LocalDateTime.now());
            System.out.println("\n📥 Order History actualize to cancel, caused by a cancelled delivery");
            channel.basicAck(tag, false);

        } catch (Exception e) {
            System.err.println("❌ Error processing DeliveryCanceledEvent in OrderHistoryService: " + e.getMessage());
            if (e instanceof OrderHistoryNotFoundException) {
                channel.basicAck(tag, false);
            }
            channel.basicNack(tag, false, true);

        }
    }

}
