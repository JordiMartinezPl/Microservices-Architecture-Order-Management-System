package cat.tecnocampus.product.adapters.in.restAPI.controllers;

import cat.tecnocampus.product.application.ports.in.ReserveStockUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ReserveStockController {
    private final ReserveStockUseCase productService;

    public ReserveStockController(ReserveStockUseCase productService) {
        this.productService = productService;
    }





    @PostMapping("/{productId}/reserve/{quantity}")
    public ResponseEntity<String> reserveStock(@PathVariable Long productId, @PathVariable int quantity) {
        productService.reserveStock(productId, quantity);
        return ResponseEntity.ok("You have your product reserved");
    }

}
