package cat.tecnocampus.order;

import cat.tecnocampus.order.adapters.out.persistence.OrderRepository;
import cat.tecnocampus.order.application.ports.in.OrderCRUD;
import cat.tecnocampus.order.application.services.OrderServices;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ServiceBeansCreation {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;

    public ServiceBeansCreation(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClient = webClientBuilder;
    }

    @Bean
    public OrderCRUD orderCRUD(RabbitTemplate rabbitTemplate, DirectExchange orderExchange, FanoutExchange deliveryCanceledExchange, FanoutExchange deliveryCannotCancelExchange) {
        return new OrderServices(orderRepository, webClient, rabbitTemplate, orderExchange, deliveryCanceledExchange, deliveryCannotCancelExchange);
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order-exchange");
    }

    @Bean
    public FanoutExchange deliveryCanceledExchange() {
        return new FanoutExchange("delivery.canceled.exchange");
    }

    @Bean
    public FanoutExchange deliveryCannotCancelExchange() {
        return new FanoutExchange("delivery.cannot.cancel.exchange");
    }

    @Bean
    public Queue orderDeliveryCanceledQueue() {
        return new Queue("order.delivery.canceled.queue", true);
    }

    @Bean
    public Binding bindingOrderDeliveryCanceled(FanoutExchange deliveryCanceledExchange, Queue orderDeliveryCanceledQueue) {
        return BindingBuilder.bind(orderDeliveryCanceledQueue).to(deliveryCanceledExchange);
    }

    @Bean
    public Queue orderDeliveryCannotCancelQueue() {
        return new Queue("order.delivery.cannot.cancel.queue", true);
    }

    @Bean
    public Binding bindingOrderDeliveryCannotCancel(FanoutExchange deliveryCannotCancelExchange, Queue orderDeliveryCannotCancelQueue) {
        return BindingBuilder.bind(orderDeliveryCannotCancelQueue).to(deliveryCannotCancelExchange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

