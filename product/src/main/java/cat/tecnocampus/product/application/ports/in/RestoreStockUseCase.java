package cat.tecnocampus.product.application.ports.in;

public interface RestoreStockUseCase {
    void restoreStock(Long productId, Integer quantity);
}


