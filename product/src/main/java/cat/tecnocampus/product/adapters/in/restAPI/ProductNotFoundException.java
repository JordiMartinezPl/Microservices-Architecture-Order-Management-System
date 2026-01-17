package cat.tecnocampus.product.adapters.in.restAPI;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super("Product with id " + id + " not found");
    }

}