package cat.tecnocampus.events;

import java.io.Serializable;

public record OrderCancelRequestEvent(Long orderId, Long productId, int quantity, double price,
                                      Long customerId) implements Serializable {
}
