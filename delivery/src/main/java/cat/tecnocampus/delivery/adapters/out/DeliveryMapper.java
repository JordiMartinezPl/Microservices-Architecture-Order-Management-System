package cat.tecnocampus.delivery.adapters.out;

import cat.tecnocampus.delivery.model.Delivery;

import java.time.LocalDateTime;

public class DeliveryMapper {

    public static DeliveryEntity toEntity(Delivery delivery) {
        if (delivery == null) {
            return null;
        }

        DeliveryEntity entity = new DeliveryEntity();
        entity.setOrderId(delivery.getOrderId());
        entity.setAddress(delivery.getAddress());
        entity.setCustomerName(delivery.getCustomerName());
        entity.setCustomerEmail(delivery.getCustomerEmail());
        entity.setStatus(DeliveryEntity.Status.valueOf(delivery.getStatus().name()));
        entity.setCreationDate(LocalDateTime.now());
        return entity;
    }

    public static Delivery toModel(DeliveryEntity entity) {
        if (entity == null) {
            return null;
        }

        Delivery delivery = new Delivery();
        delivery.setDeliveryId(entity.getId());
        delivery.setOrderId(entity.getOrderId());
        delivery.setAddress(entity.getAddress());
        delivery.setCustomerName(entity.getCustomerName());
        delivery.setCustomerEmail(entity.getCustomerEmail());
        delivery.setStatus(Delivery.Status.valueOf(entity.getStatus().name()));
        return delivery;
    }
}