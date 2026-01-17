package cat.tecnocampus.order.adapters.out.persistence;

import cat.tecnocampus.order.model.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private int quantity;
    private Long customerId;
    @Enumerated(EnumType.STRING)
    private Order.OrderStatus Status;

    public OrderEntity() {
    }

    public enum OrderStatus {
        PENDING, REJECTED, FINISHED, CANCELED
    }

}
