package cat.tecnocampus.delivery.application.ports.in;

public interface CancelDeliveryUseCase {
    boolean cancelDelivery(long deliveryId);
}
