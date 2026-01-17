package cat.tecnocampus.orderhistory.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderHistory {
    private String id;
    private long productId;
    private String productName;
    private double price;
    private int quantity;
    private long customerId;
    private String clientName;
    private String clientEmail;
    private String clientDirection;
    private long orderId;
    private LocalDateTime creationDate;
    private LocalDateTime cancellationDate;
    private LocalDateTime finalizationDate;
    private OrderHistoryStatus orderHistoryStatus;

    public OrderHistory() {
    }

    public OrderHistory(long productId,
                        String productName,
                        double price,
                        int quantity,
                        long customerId,
                        String clientName,
                        String clientEmail,
                        String clientDirection,
                        LocalDateTime creationDate,
                        long orderId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.customerId = customerId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientDirection = clientDirection;
        this.creationDate = creationDate;
        this.orderHistoryStatus = null;
        this.orderId = orderId;
        this.cancellationDate = null;
        this.finalizationDate = null;
    }

    public OrderHistory(String id,
                        long productId,
                        String productName,
                        double price,
                        int quantity,
                        long customerId,
                        String clientName,
                        String clientEmail,
                        String clientDirection,
                        LocalDateTime creationDate,
                        LocalDateTime cancellationDate,
                        LocalDateTime finalizationDate,
                        long orderId) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.customerId = customerId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientDirection = clientDirection;
        this.creationDate = creationDate;
        this.cancellationDate = cancellationDate;
        this.finalizationDate = finalizationDate;
        this.orderHistoryStatus = null;
        this.orderId = orderId;
        this.cancellationDate = null;
        this.finalizationDate = null;
    }

    public OrderHistory(String id,
                        long l,
                        String productName,
                        double price,
                        int quantity,
                        long l1,
                        String clientName,
                        String clientEmail,
                        String clientDirection,
                        LocalDateTime creationDate,
                        long orderId) {
        this.id = id;
        this.productId = l;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.customerId = l1;
        this.clientName = clientName;
        this.creationDate = creationDate;
        this.clientEmail = clientEmail;
        this.clientDirection = clientDirection;
        this.orderId = orderId;
    }

    public enum OrderHistoryStatus {
        CREATED, REJECTED, FINISHED, CANCELLED
    }
}


