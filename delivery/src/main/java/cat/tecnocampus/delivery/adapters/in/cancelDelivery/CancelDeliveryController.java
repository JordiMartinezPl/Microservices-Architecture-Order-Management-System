package cat.tecnocampus.delivery.adapters.in.cancelDelivery;

import cat.tecnocampus.delivery.application.ports.in.CancelDeliveryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class CancelDeliveryController {
    private final CancelDeliveryUseCase cancelDeliveryUseCase;

    public CancelDeliveryController(CancelDeliveryUseCase cancelDeliveryUseCase) {
        this.cancelDeliveryUseCase = cancelDeliveryUseCase;
    }

    @PutMapping("/deliveries/{deliveryId}")
    public ResponseEntity<Boolean> cancelDelivery(@PathVariable long deliveryId) {
        boolean result = cancelDeliveryUseCase.cancelDelivery(deliveryId);
        return ResponseEntity.ok(result);
    }
}
