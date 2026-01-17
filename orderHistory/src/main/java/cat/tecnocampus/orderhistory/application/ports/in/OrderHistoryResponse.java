package cat.tecnocampus.orderhistory.application.ports.in;

import cat.tecnocampus.orderhistory.model.OrderHistory;

import java.time.LocalDateTime;

public record OrderHistoryResponse(String id, String clientName, String clientEmail, String clientDirection,
                                   String productName, double price,
                                   int quantity, LocalDateTime creationDate, LocalDateTime cancellationDate,
                                   LocalDateTime finalizationDate
        , OrderHistory.OrderHistoryStatus orderHistoryStatus) {
}
