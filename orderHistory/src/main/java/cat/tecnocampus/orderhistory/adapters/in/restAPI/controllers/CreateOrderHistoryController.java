package cat.tecnocampus.orderhistory.adapters.in.restAPI.controllers;

import cat.tecnocampus.orderhistory.application.ports.in.CreateOrderHistoryUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryCommand;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderHistory")

public class CreateOrderHistoryController {

    private final CreateOrderHistoryUseCase orderHistoryService;

    public CreateOrderHistoryController(CreateOrderHistoryUseCase orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }


    @PostMapping
    public ResponseEntity<OrderHistoryResponse> createOrderHistory(@RequestBody OrderHistoryCommand orderHistoryCommand) {
        OrderHistoryResponse orderHistoryResponse = orderHistoryService.createOrderHistory(orderHistoryCommand);
        return ResponseEntity.ok(orderHistoryResponse);

    }
}
