package cat.tecnocampus.events;

import java.io.Serializable;

public record DeliveryCannotCancelEvent(Long orderId) implements Serializable {
}