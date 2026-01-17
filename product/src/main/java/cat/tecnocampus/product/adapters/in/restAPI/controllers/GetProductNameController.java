package cat.tecnocampus.product.adapters.in.restAPI.controllers;

import cat.tecnocampus.product.application.ports.in.GetProductNameUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class GetProductNameController {
    private final GetProductNameUseCase productService;

    public GetProductNameController(GetProductNameUseCase productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/name")
    public ResponseEntity<String> getProductById(@PathVariable Long productId) {
        String productResponseOptional = productService.getProductName(productId);
        return ResponseEntity.ok(productResponseOptional);
    }

}
