package cat.tecnocampus.orderhistory.adapters.in.restAPI.controllers;

import cat.tecnocampus.orderhistory.application.ports.in.GetOrderHistoryByClientIdUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.GetOrderHistoryByIdUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderHistory")
public class GetOrderHistoryByIdController {
    private final GetOrderHistoryByIdUseCase orderHistoryService;

    public GetOrderHistoryByIdController(GetOrderHistoryByIdUseCase orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderHistoryResponse> getOrderById(@PathVariable String id) {
        OrderHistoryResponse orderHistoryResponse = orderHistoryService.getOrderHistoryById(id);
        return ResponseEntity.ok(orderHistoryResponse);
    }
}
