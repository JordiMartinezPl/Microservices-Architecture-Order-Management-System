package cat.tecnocampus.orderhistory.application.ports.in;

import java.time.LocalDateTime;

public record OrderHistoryCommand(long productId, String productName, double price, int quantity, long customerId,
                                  String clientName, String clientEmail, String clientDirection,
                                  LocalDateTime creationDate,
                                  long orderId) {
}
