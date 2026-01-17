package cat.tecnocampus.order.adapters.in.restAPI;

import cat.tecnocampus.order.application.ports.in.OrderCRUD;
import cat.tecnocampus.order.application.ports.in.OrderCommand;
import cat.tecnocampus.order.application.ports.in.OrderResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
    private final OrderCRUD orderService;

    public OrderRestController(@Qualifier("orderCRUD") OrderCRUD orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCommand orderCommand) {
        orderService.createOrder(orderCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }


}
