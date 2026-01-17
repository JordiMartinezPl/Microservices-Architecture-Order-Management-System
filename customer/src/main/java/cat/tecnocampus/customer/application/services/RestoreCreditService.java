package cat.tecnocampus.customer.application.services;

import cat.tecnocampus.customer.adapters.in.restAPI.CustomerNotFoundException;
import cat.tecnocampus.customer.application.ports.in.RestoreCreditUseCase;
import cat.tecnocampus.customer.model.Customer;
import cat.tecnocampus.events.DeliveryCanceledEvent;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;


public class RestoreCreditService implements RestoreCreditUseCase {

    private final cat.tecnocampus.customer.application.ports.out.CustomerRepository customerRepository;

    public RestoreCreditService (cat.tecnocampus.customer.application.ports.out.CustomerRepository customerRepository){
        this.customerRepository= customerRepository;
    }


    @Override
    public void restoreCredit(Long id, double amount) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setCreditBalance(customer.getCreditBalance() + amount);
        customerRepository.save(customer);
    }

    @RabbitListener(queues = "customer.delivery.canceled.queue")
    public void handleDeliveryCanceledEvent(DeliveryCanceledEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            restoreCredit(event.customerId(), event.price() * event.quantity());
            System.out.println("\n📥 Customer credit restored, caused by a cancelled delivery");
            channel.basicAck(tag, false);

        } catch (Exception e) {
            System.err.println("❌ Error processing DeliveryCanceledEvent in CustomerService: " + e.getMessage());
            if (e instanceof CustomerNotFoundException) {
                channel.basicAck(tag, false);
                return;
            }
            channel.basicNack(tag, false, true);
        }
    }
}
