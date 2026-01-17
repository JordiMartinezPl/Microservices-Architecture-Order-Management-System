package cat.tecnocampus.orderhistory.adapters.out.persistence;

import cat.tecnocampus.orderhistory.model.OrderHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderHistoryMongoRepository extends MongoRepository<OrderHistoryDocument, String> {
    List<OrderHistory> findByOrderHistoryStatus(OrderHistory.OrderHistoryStatus orderHistoryStatus);

    List<OrderHistory> findByCustomerId(String customerId);

    @Query("{ 'creationDate' : { $gte: ?0, $lte: ?1 } }")
    List<OrderHistoryDocument> findByCreationDateBetween(Instant startDate, Instant endDate);

    Optional<OrderHistory> findByOrderId(String orderId);

}