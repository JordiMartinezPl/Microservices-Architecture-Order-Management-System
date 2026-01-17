package cat.tecnocampus.orderhistory.application.ports.in;

public interface CreateOrderHistoryUseCase {
    OrderHistoryResponse createOrderHistory(OrderHistoryCommand orderHistoryCommand);

}
