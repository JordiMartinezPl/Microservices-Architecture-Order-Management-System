package cat.tecnocampus.product.application.services;

import cat.tecnocampus.product.adapters.in.restAPI.ProductNotFoundException;
import cat.tecnocampus.product.application.ports.in.UpdateStatusUseCase;
import cat.tecnocampus.product.application.ports.out.ProductRepository;
import cat.tecnocampus.product.model.Product;
import org.springframework.stereotype.Service;


public class UpdateStatusServices implements UpdateStatusUseCase {
    private final ProductRepository productRepository;

    public UpdateStatusServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public void updateStatus(Long productId, Product.Status status) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
        product.setStatus(status);
        productRepository.save(product);
    }
}
