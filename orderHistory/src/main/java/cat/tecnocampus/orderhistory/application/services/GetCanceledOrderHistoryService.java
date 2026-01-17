package cat.tecnocampus.orderhistory.application.services;

import cat.tecnocampus.orderhistory.application.ports.in.GetCanceledOrderHistoryUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.Mapper;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import cat.tecnocampus.orderhistory.application.ports.out.OrderHistoryRepository;
import cat.tecnocampus.orderhistory.model.OrderHistory;

import java.util.List;
import java.util.Optional;

public class GetCanceledOrderHistoryService implements GetCanceledOrderHistoryUseCase {

    private final OrderHistoryRepository orderHistoryRepository;

    public GetCanceledOrderHistoryService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    public Optional<List<OrderHistoryResponse>> getCanceledOrderHistory() {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findByHistoryOrderStatus(OrderHistory.OrderHistoryStatus.CANCELLED);
        return Optional.of(Mapper.toOrderHistoryResponseList(orderHistoryList));
    }
}
