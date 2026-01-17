package cat.tecnocampus.order.adapters.out.persistence;

import cat.tecnocampus.order.model.Order;

public class OrderMapper {

    public static OrderEntity toEntity(Order order) {
        if (order == null) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setProductId(order.getProductId());
        orderEntity.setQuantity(order.getQuantity());
        orderEntity.setCustomerId(order.getCustomerId());
        orderEntity.setStatus(order.getStatus());

        return orderEntity;
    }

    public static Order toModel(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }

        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setProductId(orderEntity.getProductId());
        order.setQuantity(orderEntity.getQuantity());
        order.setCustomerId(orderEntity.getCustomerId());
        order.setStatus(Order.OrderStatus.valueOf(orderEntity.getStatus().name()));

        return order;
    }
}