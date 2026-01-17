package cat.tecnocampus.delivery;

import cat.tecnocampus.delivery.application.ports.in.CancelDeliveryUseCase;
import cat.tecnocampus.delivery.application.ports.in.ReserveTruckUseCase;
import cat.tecnocampus.delivery.application.ports.out.DeliveryRepository;
import cat.tecnocampus.delivery.application.services.CancelDeliveryService;
import cat.tecnocampus.delivery.application.services.ReserveTruckService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeansCreation {

    private final DeliveryRepository deliveryRepository;
    private final int truckAvailability;

    public ServiceBeansCreation(DeliveryRepository deliveryRepository, @Value("${delivery.truck.availability}") int truckAvailability) {
        this.deliveryRepository = deliveryRepository;
        this.truckAvailability = truckAvailability;
    }

    @Bean
    public ReserveTruckUseCase reserveTruckUseCase() {
        return new ReserveTruckService(deliveryRepository, truckAvailability);
    }

    @Bean
    public CancelDeliveryUseCase cancelDeliveryUseCase(RabbitTemplate rabbitTemplate, DirectExchange orderExchange, FanoutExchange deliveryCanceledExchange, FanoutExchange deliveryCannotCancelExchange) {
        return new CancelDeliveryService(deliveryRepository, rabbitTemplate, deliveryCanceledExchange, deliveryCannotCancelExchange);
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
    public Queue deliveryCancelQueue() {
        return new Queue("delivery.cancel.queue", true);
    }

    @Bean
    public Binding bindingDeliveryCancel(DirectExchange orderExchange, Queue deliveryCancelQueue) {
        return BindingBuilder.bind(deliveryCancelQueue).to(orderExchange).with("order.cancel");
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
