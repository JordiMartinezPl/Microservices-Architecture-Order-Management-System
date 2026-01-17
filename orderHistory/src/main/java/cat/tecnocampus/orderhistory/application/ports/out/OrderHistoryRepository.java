package cat.tecnocampus.orderhistory.application.ports.out;

import cat.tecnocampus.orderhistory.model.OrderHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderHistoryRepository {
    OrderHistory save(OrderHistory orderHistory);

    Optional<OrderHistory> findById(String id);

    List<OrderHistory> findByHistoryOrderStatus(OrderHistory.OrderHistoryStatus status);

    List<OrderHistory> findByCustomerId(Long customerId);

    List<OrderHistory> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<OrderHistory> findByOrderId(long orderId);
}