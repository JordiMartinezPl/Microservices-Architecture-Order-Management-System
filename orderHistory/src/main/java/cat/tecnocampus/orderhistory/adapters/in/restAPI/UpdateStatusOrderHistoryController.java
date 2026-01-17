package cat.tecnocampus.orderhistory.adapters.in.restAPI;

import cat.tecnocampus.orderhistory.application.ports.in.UpdateOrderHistoryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/orderHistory")
public class UpdateStatusOrderHistoryController {

    private final UpdateOrderHistoryUseCase orderHistoryService;

    public UpdateStatusOrderHistoryController(UpdateOrderHistoryUseCase orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @PutMapping("/{orderId}/cancelled")
    public ResponseEntity<String> updateOrderHistoryCancelled(@PathVariable long orderId) {
        orderHistoryService.updateOrderHistoryCancelled(orderId, LocalDateTime.now());
        return ResponseEntity.ok("Updated order history cancelled");
    }

    @PutMapping("/{orderId}/completed")
    public ResponseEntity<String> updateOrderHistoryCompleted(@PathVariable long orderId) {
        orderHistoryService.updateOrderHistoryCompleted(orderId, LocalDateTime.now());
        return ResponseEntity.ok("Updated order history completed");
    }

    @PutMapping("/{orderId}/rejected")
    public ResponseEntity<String> updateOrderHistoryRejected(@PathVariable long orderId) {
        {
            orderHistoryService.updateOrderHistoryRejected(orderId, LocalDateTime.now());
            return ResponseEntity.ok("Updated order history rejected");
        }
    }
}
