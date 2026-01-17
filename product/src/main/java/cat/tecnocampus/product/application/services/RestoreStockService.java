package cat.tecnocampus.product.application.services;

import cat.tecnocampus.events.DeliveryCanceledEvent;
import cat.tecnocampus.product.adapters.in.restAPI.ProductNotFoundException;
import cat.tecnocampus.product.application.ports.in.ReserveStockUseCase;
import cat.tecnocampus.product.application.ports.in.RestoreStockUseCase;
import cat.tecnocampus.product.application.ports.out.ProductRepository;
import cat.tecnocampus.product.model.Product;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

public class RestoreStockService implements RestoreStockUseCase {
    private final ProductRepository productRepository;

    public RestoreStockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public void restoreStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
        if (product.getStatus() != Product.Status.DISCONTINUED) {
            product.setStock(product.getStock() + quantity);
            if (product.getStatus() == Product.Status.OUT_OF_STOCK) {
                product.setStatus(Product.Status.AVAILABLE);
            }
            productRepository.save(product);
        }

    }
    @RabbitListener(queues = "product.delivery.canceled.queue")
    public void handleDeliveryCanceledEvent(DeliveryCanceledEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            restoreStock(event.productId(), event.quantity());
            System.out.println("\n📥 Product stock restored, caused by a cancelled delivery " + event);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.err.println("❌ Error processing DeliveryCanceledEvent in ProductService: " + e.getMessage());
            if (e instanceof ProductNotFoundException) {
                channel.basicAck(tag, false);
                return;
            }
            channel.basicNack(tag, false, true);
        }
    }
}
