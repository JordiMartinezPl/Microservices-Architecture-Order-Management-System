package cat.tecnocampus.product.adapters.in.restAPI.controllers;

import cat.tecnocampus.product.application.ports.in.GetProductNameUseCase;
import cat.tecnocampus.product.application.ports.in.GetStockByProductIdUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class GetStockByProductIdController {
    private final GetStockByProductIdUseCase productService;

    public GetStockByProductIdController(GetStockByProductIdUseCase productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/stock")
    public ResponseEntity<Integer> getStockByProductId(@PathVariable Long productId) {
        int stock = productService.getStockByProductId(productId);
        return ResponseEntity.ok(stock);
    }
}
