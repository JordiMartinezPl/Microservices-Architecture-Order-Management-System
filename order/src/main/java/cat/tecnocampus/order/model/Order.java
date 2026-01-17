package cat.tecnocampus.order.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private Long id;
    private long productId;
    private int quantity;
    private long customerId;
    private OrderStatus status;

    public Order() {
    }

    public Order(long productId, int quantity, long customerId) {
        this.productId = productId;
        this.quantity = quantity;
        this.customerId = customerId;
        this.status = OrderStatus.PENDING;
    }


    public enum OrderStatus {
        PENDING, REJECTED, FINISHED, CANCELLED
    }
}
