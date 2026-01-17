package cat.tecnocampus.orderhistory.adapters.out.persistence;

import cat.tecnocampus.orderhistory.model.OrderHistory;

import java.time.ZoneId;

public class Mapper {
    public static OrderHistoryDocument toDocument(OrderHistory orderHistory) {
        return new OrderHistoryDocument(
                orderHistory.getId(),
                String.valueOf(orderHistory.getOrderId()),
                String.valueOf(orderHistory.getProductId()),
                orderHistory.getProductName(),
                orderHistory.getPrice(),
                orderHistory.getQuantity(),
                String.valueOf(orderHistory.getCustomerId()),
                orderHistory.getClientName(),
                orderHistory.getClientEmail(),
                orderHistory.getClientDirection(),
                orderHistory.getCreationDate(),
                orderHistory.getCancellationDate(),
                orderHistory.getFinalizationDate(),
                orderHistory.getOrderHistoryStatus()
        );
    }

    public static OrderHistory toEntity(OrderHistoryDocument document) {
        OrderHistory orderHistory = new OrderHistory(
                document.getId(),
                Long.parseLong(document.getProductId()),
                document.getProductName(),
                document.getPrice(),
                document.getQuantity(),
                Long.parseLong(document.getCustomerId()),
                document.getClientName(),
                document.getClientEmail(),
                document.getClientDirection(),
                document.getCreationDate().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                document.getCancellationDate() != null ? document.getCancellationDate().atZone(ZoneId.systemDefault()).toLocalDateTime() : null,
                document.getCreationDate() != null ? document.getCreationDate().atZone(ZoneId.systemDefault()).toLocalDateTime() : null,
                Long.parseLong(document.getOrderId())
        );
        orderHistory.setOrderHistoryStatus(document.getOrderHistoryStatus());
        return orderHistory;
    }
}