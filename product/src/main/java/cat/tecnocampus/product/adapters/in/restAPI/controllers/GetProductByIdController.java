package cat.tecnocampus.product.adapters.in.restAPI.controllers;

import cat.tecnocampus.product.application.ports.in.GetProductByIdUseCase;
import cat.tecnocampus.product.application.ports.in.ProductResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class GetProductByIdController {
    private final GetProductByIdUseCase productService;

    public GetProductByIdController(GetProductByIdUseCase productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        ProductResponse productResponseOptional = productService.getProductById(productId);
        return ResponseEntity.ok(productResponseOptional);
    }

}
