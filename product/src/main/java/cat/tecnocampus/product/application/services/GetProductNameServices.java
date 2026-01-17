package cat.tecnocampus.product.application.services;

import cat.tecnocampus.product.adapters.in.restAPI.ProductNotFoundException;
import cat.tecnocampus.product.application.ports.in.GetProductNameUseCase;
import cat.tecnocampus.product.application.ports.out.ProductRepository;
import cat.tecnocampus.product.model.Product;
import com.rabbitmq.client.AMQP;
import org.springframework.stereotype.Service;


public class GetProductNameServices implements GetProductNameUseCase {
    private final ProductRepository productRepository;

    public GetProductNameServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public String getProductName(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
        return product.getName();
    }

}
