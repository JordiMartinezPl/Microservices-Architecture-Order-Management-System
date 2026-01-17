package cat.tecnocampus.product.application.ports.in;

import cat.tecnocampus.product.model.Product;

public interface UpdateStatusUseCase {
    void updateStatus(Long productId, Product.Status status);

}
