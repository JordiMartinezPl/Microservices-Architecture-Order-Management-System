package cat.tecnocampus.orderhistory.application.ports.in;

public interface GetOrderHistoryByIdUseCase {
    OrderHistoryResponse getOrderHistoryById(String id);

}
