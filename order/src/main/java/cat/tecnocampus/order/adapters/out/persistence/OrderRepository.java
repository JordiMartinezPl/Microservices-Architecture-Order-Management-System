package cat.tecnocampus.order.adapters.out.persistence;

import cat.tecnocampus.order.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepository implements cat.tecnocampus.order.application.ports.out.OrderRepository {
    private final OrderJPARepository orderJPARepository;

    public OrderRepository(OrderJPARepository orderJPARepository) {
        this.orderJPARepository = orderJPARepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = orderJPARepository.save(OrderMapper.toEntity(order));
        return OrderMapper.toModel(entity);
    }

    @Override
    public Optional<Order> getById(Long orderId) {
        return orderJPARepository.findById(orderId).map(OrderMapper::toModel);
    }
}