package cat.tecnocampus.orderhistory.application.ports.in;

import java.time.LocalDateTime;
import java.util.List;

public interface GetOrdersByDateRangeUseCase {
    List<OrderHistoryResponse> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

}
