package cat.tecnocampus.orderhistory.adapters.out.persistence;

import cat.tecnocampus.orderhistory.model.OrderHistory;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Document(collection = "orderHistory")
public class OrderHistoryDocument {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private String id;

    private String orderId;
    private String productId;
    private String productName;
    private double price;
    private int quantity;
    private String customerId;
    private String clientName;
    private String clientEmail;
    private String clientDirection;


    private Instant creationDate;
    private Instant cancellationDate;
    private Instant finalizationDate;

    private OrderHistory.OrderHistoryStatus orderHistoryStatus;

    public OrderHistoryDocument() {
    }

    public OrderHistoryDocument(String id,
                                String orderId,
                                String productId,
                                String productName,
                                double price,
                                int quantity,
                                String customerId,
                                String clientName,
                                String clientEmail,
                                String clientDirection,
                                LocalDateTime creationDate,
                                OrderHistory.OrderHistoryStatus orderHistoryStatus) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.customerId = customerId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientDirection = clientDirection;
        this.creationDate = (creationDate != null) ? creationDate.atZone(ZoneId.of("UTC")).toInstant() : null;
        this.orderHistoryStatus = orderHistoryStatus;
    }

    // Constructor con todos los campos
    public OrderHistoryDocument(String id,
                                String orderId,
                                String productId,
                                String productName,
                                double price,
                                int quantity,
                                String customerId,
                                String clientName,
                                String clientEmail,
                                String clientDirection,
                                LocalDateTime creationDate,
                                LocalDateTime cancellationDate,
                                LocalDateTime finalizationDate,
                                OrderHistory.OrderHistoryStatus orderHistoryStatus) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.customerId = customerId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientDirection = clientDirection;
        this.creationDate = (creationDate != null) ? creationDate.atZone(ZoneId.of("UTC")).toInstant() : null;
        this.cancellationDate = (cancellationDate != null) ? cancellationDate.atZone(ZoneId.of("UTC")).toInstant() : null;
        this.finalizationDate = (finalizationDate != null) ? finalizationDate.atZone(ZoneId.of("UTC")).toInstant() : null;
        this.orderHistoryStatus = orderHistoryStatus;
    }
}