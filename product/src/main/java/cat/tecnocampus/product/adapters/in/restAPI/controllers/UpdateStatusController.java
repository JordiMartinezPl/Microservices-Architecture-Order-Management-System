package cat.tecnocampus.product.adapters.in.restAPI.controllers;

import cat.tecnocampus.product.application.ports.in.UpdateStatusUseCase;
import cat.tecnocampus.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class UpdateStatusController {
    private final UpdateStatusUseCase productService;

    public UpdateStatusController(UpdateStatusUseCase productService) {
        this.productService = productService;
    }


    @PutMapping("/{productId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long productId, @RequestParam Product.Status status) {
        productService.updateStatus(productId, status);
        return ResponseEntity.ok("Product status updated");
    }

}
