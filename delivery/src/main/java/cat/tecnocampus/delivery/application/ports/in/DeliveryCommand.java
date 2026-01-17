package cat.tecnocampus.delivery.application.ports.in;

public record DeliveryCommand(long orderId, String address, String customerName, String customerEmail) {
}
