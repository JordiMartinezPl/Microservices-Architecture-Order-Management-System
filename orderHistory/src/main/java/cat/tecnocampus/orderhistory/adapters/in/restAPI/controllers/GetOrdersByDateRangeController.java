package cat.tecnocampus.orderhistory.adapters.in.restAPI.controllers;

import cat.tecnocampus.orderhistory.application.ports.in.GetOrderHistoryByIdUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.GetOrdersByDateRangeUseCase;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orderHistory")
public class GetOrdersByDateRangeController {
    private final GetOrdersByDateRangeUseCase orderHistoryService;

    public GetOrdersByDateRangeController(GetOrdersByDateRangeUseCase orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<OrderHistoryResponse>> getOrdersByDateRange(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(orderHistoryService.getOrdersByDateRange(startDate, endDate));
    }
}
