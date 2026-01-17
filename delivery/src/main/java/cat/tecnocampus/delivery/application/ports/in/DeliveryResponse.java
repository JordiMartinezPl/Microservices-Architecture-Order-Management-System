package cat.tecnocampus.delivery.application.ports.in;

public record DeliveryResponse(long id, long orderId, String address, String customerName, String customerEmail) {
}
