package cat.tecnocampus.product.application.ports.in;

import cat.tecnocampus.product.model.Product;

public record ProductResponse(long id, String name, String description, double price, int stock,
                              Product.Status status) {
}
