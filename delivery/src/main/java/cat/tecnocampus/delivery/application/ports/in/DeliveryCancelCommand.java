package cat.tecnocampus.delivery.application.ports.in;

public record DeliveryCancelCommand(long orderId, long productId, double price, int quantity) {
}
