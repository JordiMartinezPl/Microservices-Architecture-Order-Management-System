package cat.tecnocampus.order.application.ports.out;


import cat.tecnocampus.order.model.Order;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> getById(Long orderId);
}
