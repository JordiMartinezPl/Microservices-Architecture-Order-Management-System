package cat.tecnocampus.delivery.application.ports.in;

import java.util.Optional;

public interface ReserveTruckUseCase {
    Optional<DeliveryResponse> reserveTruck(DeliveryCommand delivery);
}
