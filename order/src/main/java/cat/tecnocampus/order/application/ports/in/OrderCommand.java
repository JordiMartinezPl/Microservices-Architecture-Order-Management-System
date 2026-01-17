package cat.tecnocampus.order.application.ports.in;


public record OrderCommand(long productId, int quantity, long customerId) {
}
