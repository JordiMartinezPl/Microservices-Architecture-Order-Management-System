package cat.tecnocampus.orderhistory.application.ports.in;

import java.util.List;
import java.util.Optional;

public interface GetCanceledOrderHistoryUseCase {
    Optional<List<OrderHistoryResponse>> getCanceledOrderHistory();

}
