package cat.tecnocampus.order.application.ports.in;

public interface OrderCRUD {
    void createOrder(OrderCommand order);

    void cancelOrder(Long orderId);

}
