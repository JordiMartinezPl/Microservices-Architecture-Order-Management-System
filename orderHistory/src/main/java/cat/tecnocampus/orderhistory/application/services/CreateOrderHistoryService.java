package cat.tecnocampus.orderhistory.application.services;

import cat.tecnocampus.orderhistory.application.ports.in.Mapper;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryCommand;
import cat.tecnocampus.orderhistory.application.ports.in.OrderHistoryResponse;
import cat.tecnocampus.orderhistory.application.ports.out.OrderHistoryRepository;
import cat.tecnocampus.orderhistory.model.OrderHistory;
import cat.tecnocampus.orderhistory.application.ports.in.CreateOrderHistoryUseCase;

public class CreateOrderHistoryService implements CreateOrderHistoryUseCase {
    private final OrderHistoryRepository orderHistoryRepository;

    public CreateOrderHistoryService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    public OrderHistoryResponse createOrderHistory(OrderHistoryCommand orderHistoryCommand) {
        OrderHistory orderHistory = new OrderHistory(orderHistoryCommand.productId(),
                orderHistoryCommand.productName(),
                orderHistoryCommand.price(),
                orderHistoryCommand.quantity(),
                orderHistoryCommand.customerId(),
                orderHistoryCommand.clientName(),
                orderHistoryCommand.clientEmail(),
                orderHistoryCommand.clientDirection(),
                orderHistoryCommand.creationDate(),
                orderHistoryCommand.orderId());
        return Mapper.toOrderHistoryResponse(orderHistoryRepository.save(orderHistory));
    }

}
