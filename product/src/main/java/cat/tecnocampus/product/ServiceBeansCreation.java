package cat.tecnocampus.product;

import cat.tecnocampus.product.application.ports.in.*;
import cat.tecnocampus.product.application.ports.out.ProductRepository;
import cat.tecnocampus.product.application.services.*;
import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component("productServiceBeansCreation")
@Configuration
public class ServiceBeansCreation {

    private final ProductRepository productRepository;

    public ServiceBeansCreation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Bean
    public GetPriceUseCase priceUseCase() {
        return new GetPriceService(productRepository);
    }
    @Bean
    public GetProductByIdUseCase GetProductByIdUseCase() {
        return new GetProductByIdServices(productRepository);
    }
    @Bean
    public GetProductNameUseCase getProductNameUseCase() {
        return new GetProductNameServices(productRepository);
    }
    @Bean
    public GetStockByProductIdUseCase getStockByProductIdUseCase() {
        return new GetStockByProductIdServices(productRepository);
    }

    @Bean
    public ReserveStockUseCase reserveStockUseCase() {
        return new ReserveStockServices(productRepository);
    }
    @Bean
    public RestoreStockService restoreStockService() {
        return new RestoreStockService(productRepository);
    }
    @Bean
    public UpdateStatusUseCase updateStatusUseCase() {
        return new UpdateStatusServices(productRepository);
    }





    @Bean
    public FanoutExchange deliveryCanceledExchange() {
        return new FanoutExchange("delivery.canceled.exchange");
    }

    @Bean
    public Queue productDeliveryCanceledQueue() {
        return new Queue("product.delivery.canceled.queue", true);
    }

    @Bean
    public Binding bindingProductDeliveryCanceled(FanoutExchange deliveryCanceledExchange, Queue productDeliveryCanceledQueue) {
        return BindingBuilder.bind(productDeliveryCanceledQueue).to(deliveryCanceledExchange);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
