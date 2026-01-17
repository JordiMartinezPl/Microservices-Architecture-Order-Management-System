package cat.tecnocampus.delivery.model;

public class Delivery {
    private long orderId;
    private long deliveryId;
    private Status status;
    private String address;
    private String customerName;
    private String customerEmail;

    public Delivery(long orderId, String address, String customerName, String customerEmail) {
        this.orderId = orderId;
        this.address = address;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.status = Status.IN_PREPARATION;
    }

    public Delivery() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void send() {
        if (status == Status.IN_PREPARATION)
            this.status = Status.SENT;
        else
            throw new IllegalStateException("Delivery is not in preparation");
    }

    public void cancel() {
        if (status == Status.IN_PREPARATION)
            this.status = Status.CANCELED;
        else
            throw new IllegalStateException("Delivery is not in preparation");
    }

    public enum Status {
        IN_PREPARATION,
        SENT,
        CANCELED
    }
}
