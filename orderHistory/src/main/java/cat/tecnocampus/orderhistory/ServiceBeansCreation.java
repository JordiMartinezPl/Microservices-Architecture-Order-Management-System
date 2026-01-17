package cat.tecnocampus.orderhistory;

import cat.tecnocampus.orderhistory.application.ports.in.*;
import cat.tecnocampus.orderhistory.application.ports.out.OrderHistoryRepository;
import cat.tecnocampus.orderhistory.application.services.*;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeansCreation {

    private final OrderHistoryRepository orderHistoryRepository;

    public ServiceBeansCreation(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Bean
    public CreateOrderHistoryService createOrderHistoryService() {
        return new CreateOrderHistoryService(orderHistoryRepository);
    }
    @Bean
    public GetCanceledOrderHistoryUseCase getCanceledOrderHistoryUseCase() {
        return new GetCanceledOrderHistoryService(orderHistoryRepository);
    }
    @Bean
    public GetCompletedOrderHistoryUseCase getCompletedOrderHistoryUseCase() {
        return new GetCompletedOrderHistoryService(orderHistoryRepository);
    }
    @Bean
    public GetOrderHistoryByClientIdUseCase getOrderHistoryByClientIdUseCase() {
        return new GetOrderHistoryByClientIdService(orderHistoryRepository);
    }
    @Bean
    public GetOrderHistoryByIdUseCase getOrderHistoryByIdUseCase() {
        return new GetOrderHistoryByIdService(orderHistoryRepository);
    }
    @Bean
    public UpdateOrderHistoryService updateOrderHistoryService() {
        return new UpdateOrderHistoryService(orderHistoryRepository);
    }
    @Bean
    public GetOrdersByDateRangeUseCase getOrdersByDateRangeUseCase() {
        return new GetOrdersByDateRangeService(orderHistoryRepository);
    }



    @Bean
    public FanoutExchange deliveryCanceledExchange() {
        return new FanoutExchange("delivery.canceled.exchange");
    }

    @Bean
    public Queue orderHistoryDeliveryCanceledQueue() {
        return new Queue("orderhistory.delivery.canceled.queue", true);
    }

    @Bean
    public Binding bindingOrderHistoryDeliveryCanceled(FanoutExchange deliveryCanceledExchange, Queue orderHistoryDeliveryCanceledQueue) {
        return BindingBuilder.bind(orderHistoryDeliveryCanceledQueue).to(deliveryCanceledExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
