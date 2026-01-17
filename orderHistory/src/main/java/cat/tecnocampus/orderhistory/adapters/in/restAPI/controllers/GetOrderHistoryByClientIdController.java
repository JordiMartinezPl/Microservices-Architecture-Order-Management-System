package cat.tecnocampus.orderhistory.adapters.in.restAPI.controllers;

import cat.tecnocampus.orderhistory.application.ports.in.GetCompletedOrderHistoryUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.GetOrderHistoryByClientIdUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderHistory")
public class GetOrderHistoryByClientIdController {
    private final GetOrderHistoryByClientIdUseCase orderHistoryService;

    public GetOrderHistoryByClientIdController(GetOrderHistoryByClientIdUseCase orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderHistoryResponse>> getOrderHistoryByClientId(@PathVariable Long clientId) {
        return orderHistoryService.getOrderHistoryByClientId(clientId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
