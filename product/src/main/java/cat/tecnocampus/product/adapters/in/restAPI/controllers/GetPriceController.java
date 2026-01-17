package cat.tecnocampus.product.adapters.in.restAPI.controllers;

import cat.tecnocampus.product.application.ports.in.GetPriceUseCase;
import cat.tecnocampus.product.application.ports.in.GetProductByIdUseCase;
import cat.tecnocampus.product.application.ports.in.ProductPriceResponse;
import cat.tecnocampus.product.application.ports.in.ProductResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class GetPriceController {
    private final GetPriceUseCase productService;

    public GetPriceController(GetPriceUseCase productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/price/{quantity}")
    public ResponseEntity<ProductPriceResponse> getPrice(@PathVariable Long productId, @PathVariable int quantity) {
        ProductPriceResponse price = productService.getPrice(productId, quantity);
        return ResponseEntity.ok(price);
    }
}
