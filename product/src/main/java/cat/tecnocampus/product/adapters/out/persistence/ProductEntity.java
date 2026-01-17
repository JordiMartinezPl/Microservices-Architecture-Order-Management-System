package cat.tecnocampus.product.adapters.out.persistence;

import cat.tecnocampus.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "product")
public class ProductEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    @Enumerated(EnumType.STRING)
    private Product.Status Status;

    public ProductEntity() {
    }

    public enum Status {
        AVAILABLE,
        OUT_OF_STOCK,
        DISCONTINUED
    }

}
