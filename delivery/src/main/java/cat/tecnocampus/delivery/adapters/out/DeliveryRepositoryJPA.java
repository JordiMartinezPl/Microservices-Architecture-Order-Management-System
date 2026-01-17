package cat.tecnocampus.delivery.adapters.out;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepositoryJPA extends JpaRepository<DeliveryEntity, Long> {
    Optional<DeliveryEntity> findById(long deliveryId);

    @Transactional
    @Modifying
    @Query("UPDATE delivery d SET d.status = :status WHERE d.id = :deliveryId")
    void updateDeliveryStatus(long deliveryId, DeliveryEntity.Status status);

    List<DeliveryEntity> findByStatus(DeliveryEntity.Status status);

    Optional<DeliveryEntity> findByOrderId(long orderId);
}
