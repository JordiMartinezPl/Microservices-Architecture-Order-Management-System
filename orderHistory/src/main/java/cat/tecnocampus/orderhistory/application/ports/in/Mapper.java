package cat.tecnocampus.orderhistory.application.ports.in;

import cat.tecnocampus.orderhistory.model.OrderHistory;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    public static OrderHistoryResponse toOrderHistoryResponse(OrderHistory orderHistory) {
        return new OrderHistoryResponse(
                orderHistory.getId(),
                orderHistory.getClientName(),
                orderHistory.getClientEmail(),
                orderHistory.getClientDirection(),
                orderHistory.getProductName(),
                orderHistory.getPrice(),
                orderHistory.getQuantity(),
                orderHistory.getCreationDate(),
                orderHistory.getCancellationDate(),
                orderHistory.getFinalizationDate(),
                orderHistory.getOrderHistoryStatus()
        );
    }

    public static List<OrderHistoryResponse> toOrderHistoryResponseList(List<OrderHistory> orderHistoryList) {
        return orderHistoryList.stream()
                .map(Mapper::toOrderHistoryResponse)
                .collect(Collectors.toList());
    }
}