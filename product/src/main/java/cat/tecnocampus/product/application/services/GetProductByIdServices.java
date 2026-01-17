package cat.tecnocampus.product.application.services;

import cat.tecnocampus.product.adapters.in.restAPI.ProductNotFoundException;
import cat.tecnocampus.product.application.ports.in.GetProductByIdUseCase;
import cat.tecnocampus.product.application.ports.in.ProductResponse;
import cat.tecnocampus.product.application.ports.out.ProductRepository;
import cat.tecnocampus.product.model.Product;
import org.springframework.stereotype.Service;

public class GetProductByIdServices implements GetProductByIdUseCase {
    private final ProductRepository productRepository;

    public GetProductByIdServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock(), product.getStatus());

    }
}
