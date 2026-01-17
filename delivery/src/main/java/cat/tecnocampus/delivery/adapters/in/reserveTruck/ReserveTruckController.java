package cat.tecnocampus.delivery.adapters.in.reserveTruck;

import cat.tecnocampus.delivery.application.ports.in.DeliveryCommand;
import cat.tecnocampus.delivery.application.ports.in.DeliveryResponse;
import cat.tecnocampus.delivery.application.ports.in.ReserveTruckUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class ReserveTruckController {
    private final ReserveTruckUseCase reserveTruckUseCase;

    public ReserveTruckController(ReserveTruckUseCase reserveTruckUseCase) {
        this.reserveTruckUseCase = reserveTruckUseCase;
    }

    @PostMapping("/deliveries")
    public ResponseEntity<DeliveryResponse> reserveTruck(@RequestBody DeliveryCommand delivery) {
        Optional<DeliveryResponse> deliveryOptional = reserveTruckUseCase.reserveTruck(delivery);
        return deliveryOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
