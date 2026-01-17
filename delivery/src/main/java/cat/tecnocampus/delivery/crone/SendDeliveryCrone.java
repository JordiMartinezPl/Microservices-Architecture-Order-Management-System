package cat.tecnocampus.delivery.crone;

import cat.tecnocampus.delivery.adapters.out.DeliveryEntity;
import cat.tecnocampus.delivery.adapters.out.DeliveryRepositoryJPA;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

// crone that sends the delivery (updates the state to sent). It should be executed every 10 seconds
@Component
public class SendDeliveryCrone {

    private final DeliveryRepositoryJPA repositoryJPA;
    private final int deliverInSeconds;

    public SendDeliveryCrone(DeliveryRepositoryJPA repositoryJPA, @Value("${delivery.sendAfter}") int deliverInSeconds) {
        this.repositoryJPA = repositoryJPA;
        this.deliverInSeconds = deliverInSeconds;
    }

    @Transactional
    @Scheduled(cron = "*/10 * * * * *")
    public void sendDeliveriesAfterXSeconds() {
        List<DeliveryEntity> deliveries = repositoryJPA.findByStatus(DeliveryEntity.Status.IN_PREPARATION);
        deliveries.forEach(delivery -> {
            if (LocalDateTime.now().isAfter(delivery.getCreationDate().plusSeconds(deliverInSeconds))) {
                repositoryJPA.updateDeliveryStatus(delivery.getId(), DeliveryEntity.Status.SENT);
            }
        });
    }
}