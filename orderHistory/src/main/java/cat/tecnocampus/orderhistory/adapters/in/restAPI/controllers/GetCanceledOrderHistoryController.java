package cat.tecnocampus.orderhistory.adapters.in.restAPI.controllers;

import cat.tecnocampus.orderhistory.application.ports.in.GetCanceledOrderHistoryUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/orderHistory")
public class GetCanceledOrderHistoryController {
    private final GetCanceledOrderHistoryUseCase orderHistoryService;

    public GetCanceledOrderHistoryController(GetCanceledOrderHistoryUseCase orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping("/cancelled")
    public ResponseEntity<List<OrderHistoryResponse>> getCanceledOrderHistory() {
        return orderHistoryService.getCanceledOrderHistory()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
