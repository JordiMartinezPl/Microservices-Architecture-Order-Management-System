package cat.tecnocampus.product.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private Status status;

    public Product() {
    }

    public Product(String name, String description, double price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = Status.AVAILABLE;
    }

    public enum Status {
        AVAILABLE,
        OUT_OF_STOCK,
        DISCONTINUED
    }
}
