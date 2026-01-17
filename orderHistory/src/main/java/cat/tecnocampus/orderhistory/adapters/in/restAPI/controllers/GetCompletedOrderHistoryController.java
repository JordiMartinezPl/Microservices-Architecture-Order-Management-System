package cat.tecnocampus.orderhistory.adapters.in.restAPI.controllers;

import cat.tecnocampus.orderhistory.application.ports.in.CreateOrderHistoryUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.GetCompletedOrderHistoryUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import cat.tecnocampus.orderhistory.application.services.GetCompletedOrderHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderHistory")
public class GetCompletedOrderHistoryController {
    private final GetCompletedOrderHistoryUseCase orderHistoryService;

    public GetCompletedOrderHistoryController(GetCompletedOrderHistoryUseCase orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }
    @GetMapping("/finished")
    public ResponseEntity<List<OrderHistoryResponse>> getFinishedOrderHistory() {
        return orderHistoryService.getCompletedOrderHistory()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


}
