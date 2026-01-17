package cat.tecnocampus.order.application.ports.in;

public record OrderResponse(long id, long productId, int quantity, long customerId) {
}
