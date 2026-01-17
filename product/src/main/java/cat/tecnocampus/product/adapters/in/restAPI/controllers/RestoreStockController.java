package cat.tecnocampus.product.adapters.in.restAPI.controllers;

import cat.tecnocampus.product.application.ports.in.RestoreStockUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class RestoreStockController {

    private final RestoreStockUseCase productService;

    public RestoreStockController(RestoreStockUseCase productService) {
        this.productService = productService;
    }

    @PostMapping("/{productId}/restore/{quantity}")
    public ResponseEntity<String> restoreStock(@PathVariable Long productId, @PathVariable int quantity) {
        productService.restoreStock(productId, quantity);
        return ResponseEntity.ok("You have your product restored.");
    }

}
