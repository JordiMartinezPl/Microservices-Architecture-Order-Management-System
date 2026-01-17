package cat.tecnocampus.delivery.application.ports.out;

import cat.tecnocampus.delivery.model.Delivery;

import java.util.Optional;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);

    void updateDeliveryStatus(long deliveryId, Delivery.Status status);

    Optional<Delivery> findById(long deliveryId);

    Optional<Delivery> findByOrderId(long orderId);
}
