package cat.tecnocampus.orderhistory.adapters.out.persistence;

import cat.tecnocampus.orderhistory.application.ports.out.OrderHistoryRepository;
import cat.tecnocampus.orderhistory.model.OrderHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderHistoryMongoRepositoryImpl implements OrderHistoryRepository {

    private final OrderHistoryMongoRepository mongoRepository;

    @Autowired
    public OrderHistoryMongoRepositoryImpl(@Lazy OrderHistoryMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public OrderHistory save(OrderHistory orderHistory) {
        OrderHistoryDocument orderHistoryDocument = mongoRepository.save(Mapper.toDocument(orderHistory));
        return Mapper.toEntity(orderHistoryDocument);
    }

    @Override
    public Optional<OrderHistory> findById(String id) {
        Optional<OrderHistoryDocument> documentOpt = mongoRepository.findById(String.valueOf(id));
        return documentOpt.map(Mapper::toEntity);
    }

    @Override
    public List<OrderHistory> findByHistoryOrderStatus(OrderHistory.OrderHistoryStatus status) {
        return mongoRepository.findByOrderHistoryStatus(status);
    }

    @Override
    public List<OrderHistory> findByCustomerId(Long customerId) {
        return mongoRepository.findByCustomerId(String.valueOf(customerId));
    }

    @Override
    public List<OrderHistory> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        ZoneId utcZone = ZoneId.of("UTC");

        Instant startInstant = startDate.atZone(utcZone).toInstant();
        Instant endInstant = endDate.atZone(utcZone).toInstant();

        List<OrderHistoryDocument> documents = mongoRepository.findByCreationDateBetween(startInstant, endInstant);
        return documents.stream().map(Mapper::toEntity).toList();
    }

    @Override
    public Optional<OrderHistory> findByOrderId(long orderId) {
        return mongoRepository.findByOrderId(String.valueOf(orderId));

    }
}