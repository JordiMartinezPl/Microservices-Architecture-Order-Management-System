package cat.tecnocampus.product.application.ports.in;

public interface GetProductByIdUseCase {
    ProductResponse getProductById(Long productId);

}
