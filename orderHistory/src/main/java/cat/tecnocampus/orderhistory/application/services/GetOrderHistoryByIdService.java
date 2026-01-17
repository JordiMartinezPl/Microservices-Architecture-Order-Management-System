package cat.tecnocampus.orderhistory.application.services;

import cat.tecnocampus.orderhistory.adapters.in.restAPI.OrderHistoryNotFoundException;
import cat.tecnocampus.orderhistory.application.ports.in.Mapper;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import cat.tecnocampus.orderhistory.application.ports.out.OrderHistoryRepository;
import cat.tecnocampus.orderhistory.model.OrderHistory;
import cat.tecnocampus.orderhistory.application.ports.in.GetOrderHistoryByIdUseCase;
public class GetOrderHistoryByIdService implements GetOrderHistoryByIdUseCase {
    private final OrderHistoryRepository orderHistoryRepository;

    public GetOrderHistoryByIdService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }


    @Override
    public OrderHistoryResponse getOrderHistoryById(String id) {
        OrderHistory orderHistoryDocument = orderHistoryRepository.findById(id)
                .orElseThrow(() -> new OrderHistoryNotFoundException(" OrderHistory with id: " + id + " not found"));
        return Mapper.toOrderHistoryResponse(orderHistoryDocument);
    }
}
