package cat.tecnocampus.orderhistory.application.ports.in;

import java.time.LocalDateTime;

public interface UpdateOrderHistoryUseCase {

    void updateOrderHistoryRejected(long orderId, LocalDateTime rejectedDate);

    void updateOrderHistoryCompleted(long orderId, LocalDateTime completedDate);

    void updateOrderHistoryCancelled(long orderId, LocalDateTime cancelledDate);


}