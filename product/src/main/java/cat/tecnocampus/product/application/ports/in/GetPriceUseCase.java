package cat.tecnocampus.product.application.ports.in;

public interface GetPriceUseCase {
    ProductPriceResponse getPrice(Long productId, int quantity);

}
