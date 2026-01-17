package cat.tecnocampus.orderhistory.application.services;

import cat.tecnocampus.orderhistory.application.ports.in.GetOrderHistoryByClientIdUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.Mapper;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import cat.tecnocampus.orderhistory.application.ports.out.OrderHistoryRepository;
import cat.tecnocampus.orderhistory.model.OrderHistory;

import java.util.List;
import java.util.Optional;

public class GetOrderHistoryByClientIdService implements GetOrderHistoryByClientIdUseCase {
    private final OrderHistoryRepository orderHistoryRepository;

    public GetOrderHistoryByClientIdService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    public Optional<List<OrderHistoryResponse>> getOrderHistoryByClientId(Long clientId) {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findByCustomerId(clientId);
        return Optional.of(Mapper.toOrderHistoryResponseList(orderHistoryList));
    }
}
