package cat.tecnocampus.customer;

import cat.tecnocampus.customer.application.ports.in.*;
import cat.tecnocampus.customer.adapters.out.persistence.CustomerRepository;
import cat.tecnocampus.customer.application.services.*;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component("customerServiceBeansCreation")
@Configuration
public class ServiceBeansCreation {

    private final CustomerRepository customerRepository;

    public ServiceBeansCreation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Bean
    public CheckSufficientCreditUseCase createCheckSufficientCreditService(){return new CheckSufficientCreditService(customerRepository);}

    @Bean
    public RestoreCreditUseCase createRestoreCreditUseCase(){
        return new RestoreCreditService(customerRepository);
    }

    @Bean
    public GetCustomerUseCase createGetCustomerUserCase(){
        return new GetCustomerService(customerRepository);
    }

    @Bean
    public DeductCreditUseCase createDeductUseCase(){
        return new DeductCreditService(customerRepository);
    }

    @Bean
    public FanoutExchange deliveryCanceledExchange() {
        return new FanoutExchange("delivery.canceled.exchange");
    }

    @Bean
    public Queue customerDeliveryCanceledQueue() {
        return new Queue("customer.delivery.canceled.queue", true);
    }

    @Bean
    public Binding bindingCustomerDeliveryCanceled(FanoutExchange deliveryCanceledExchange, Queue customerDeliveryCanceledQueue) {
        return BindingBuilder.bind(customerDeliveryCanceledQueue).to(deliveryCanceledExchange);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
