package cat.tecnocampus.orderhistory.application.services;

import cat.tecnocampus.orderhistory.application.ports.in.GetCompletedOrderHistoryUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.Mapper;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import cat.tecnocampus.orderhistory.application.ports.out.OrderHistoryRepository;
import cat.tecnocampus.orderhistory.model.OrderHistory;

import java.util.List;
import java.util.Optional;

public class GetCompletedOrderHistoryService implements GetCompletedOrderHistoryUseCase {
    private final OrderHistoryRepository orderHistoryRepository;

    public GetCompletedOrderHistoryService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    public Optional<List<OrderHistoryResponse>> getCompletedOrderHistory() {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findByHistoryOrderStatus(OrderHistory.OrderHistoryStatus.FINISHED);
        return Optional.of(Mapper.toOrderHistoryResponseList(orderHistoryList));
    }
}
