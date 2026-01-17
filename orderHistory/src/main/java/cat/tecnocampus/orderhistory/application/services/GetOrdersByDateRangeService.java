package cat.tecnocampus.orderhistory.application.services;

import cat.tecnocampus.orderhistory.application.ports.in.GetOrdersByDateRangeUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.Mapper;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import cat.tecnocampus.orderhistory.application.ports.out.OrderHistoryRepository;
import cat.tecnocampus.orderhistory.model.OrderHistory;

import java.time.LocalDateTime;
import java.util.List;

public class GetOrdersByDateRangeService implements GetOrdersByDateRangeUseCase {
    private final OrderHistoryRepository orderHistoryRepository;

    public GetOrdersByDateRangeService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }







    @Override
    public List<OrderHistoryResponse> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("StartDate must be before endDate");
        }
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findByCreationDateBetween(startDate, endDate);
        return Mapper.toOrderHistoryResponseList(orderHistoryList);
    }
}
