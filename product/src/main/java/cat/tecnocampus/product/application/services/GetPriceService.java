package cat.tecnocampus.product.application.services;

import cat.tecnocampus.product.adapters.in.restAPI.ProductNotFoundException;
import cat.tecnocampus.product.application.ports.in.GetPriceUseCase;
import cat.tecnocampus.product.application.ports.in.ProductPriceResponse;
import cat.tecnocampus.product.application.ports.out.ProductRepository;
import cat.tecnocampus.product.model.Product;
import org.springframework.stereotype.Service;


public class GetPriceService implements GetPriceUseCase {

    private final ProductRepository productRepository;

    public GetPriceService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductPriceResponse getPrice(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
        return new ProductPriceResponse(product.getPrice() * quantity);
    }
}
