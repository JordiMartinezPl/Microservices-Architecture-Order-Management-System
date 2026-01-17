package cat.tecnocampus.product.application.ports.in;

import cat.tecnocampus.product.model.Product;

public interface ReserveStockUseCase {
    void reserveStock(Long productId, Integer quantity);
    boolean checkStock(Product product, int quantity);

}
