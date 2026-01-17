package cat.tecnocampus.product.application.services;

import cat.tecnocampus.product.adapters.in.restAPI.ProductNotFoundException;
import cat.tecnocampus.product.adapters.in.restAPI.ProductReserveException;
import cat.tecnocampus.product.application.ports.in.ReserveStockUseCase;
import cat.tecnocampus.product.application.ports.out.ProductRepository;
import cat.tecnocampus.product.model.Product;
import org.springframework.stereotype.Service;


public class ReserveStockServices implements ReserveStockUseCase {
    private final ProductRepository productRepository;

    public ReserveStockServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }




    @Override
    public void reserveStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
        if (!checkStock(product, quantity)) throw new ProductReserveException("Not possible to reserve stock");
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
    @Override
    public boolean checkStock(Product product, int quantity) {
        return product.getStatus().equals(Product.Status.AVAILABLE) && quantity > 0 && quantity <= product.getStock();
    }

}
