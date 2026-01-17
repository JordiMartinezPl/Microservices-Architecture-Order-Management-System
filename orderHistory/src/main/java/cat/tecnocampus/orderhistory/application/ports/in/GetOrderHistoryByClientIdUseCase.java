package cat.tecnocampus.orderhistory.application.ports.in;

import java.util.List;
import java.util.Optional;

public interface GetOrderHistoryByClientIdUseCase {
    Optional<List<OrderHistoryResponse>> getOrderHistoryByClientId(Long clientId);

}
