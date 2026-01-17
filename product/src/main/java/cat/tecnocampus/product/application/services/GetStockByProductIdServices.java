package cat.tecnocampus.product.application.services;

import cat.tecnocampus.product.adapters.in.restAPI.ProductNotFoundException;
import cat.tecnocampus.product.application.ports.in.GetStockByProductIdUseCase;
import cat.tecnocampus.product.application.ports.out.ProductRepository;
import cat.tecnocampus.product.model.Product;
import org.springframework.stereotype.Service;


public class GetStockByProductIdServices implements GetStockByProductIdUseCase {
    private final ProductRepository productRepository;

    public GetStockByProductIdServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public int getStockByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
        return product.getStock();
    }

}
