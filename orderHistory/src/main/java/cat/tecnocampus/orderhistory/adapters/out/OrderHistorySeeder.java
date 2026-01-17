package cat.tecnocampus.orderhistory.adapters.out;

import cat.tecnocampus.orderhistory.adapters.out.persistence.OrderHistoryDocument;
import cat.tecnocampus.orderhistory.adapters.out.persistence.OrderHistoryMongoRepository;
import cat.tecnocampus.orderhistory.model.OrderHistory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class OrderHistorySeeder {

    @Bean
    CommandLineRunner initDatabase(OrderHistoryMongoRepository orderHistoryRepository) {
        return args -> {
            // define UTC zone
            ZoneId utcZone = ZoneId.of("UTC");

            LocalDateTime now = LocalDateTime.now(utcZone);

            // Inserting demo order history data
            OrderHistoryDocument order1 = new OrderHistoryDocument(
                    "123456789", "7657645", "101", "Chess Board Classic",
                    49.99, 2, "1", "John Doe", "john.doe@example.com", "123 Main St",
                    now, OrderHistory.OrderHistoryStatus.CREATED
            );

            OrderHistoryDocument order2 = new OrderHistoryDocument(
                    "2234567891", "4546", "102", "Metal Chess Set",
                    120.0, 1, "2", "Jane Smith", "jane.smith@example.com", "456 Oak St",
                    now, OrderHistory.OrderHistoryStatus.FINISHED
            );

            OrderHistoryDocument order3 = new OrderHistoryDocument(
                    "3465789123", "42667", "103", "Plastic Chess Set",
                    20.0, 3, "3", "Alice Johnson", "alice.johnson@example.com", "789 Pine St",
                    now, OrderHistory.OrderHistoryStatus.CANCELLED
            );

            OrderHistoryDocument order4 = new OrderHistoryDocument(
                    "4348", "4756687", "104", "Wooden Chess Set",
                    75.0, 1, "4", "Bob Brown", "bob.brown@example.com", "321 Elm St",
                    now, OrderHistory.OrderHistoryStatus.CANCELLED
            );

            OrderHistoryDocument order5 = new OrderHistoryDocument(
                    "554", "27892", "105", "Glass Chess Set",
                    150.0, 1, "5", "Charlie Davis", "charlie.davis@example.com", "654 Maple St",
                    now, OrderHistory.OrderHistoryStatus.REJECTED
            );

            OrderHistoryDocument order6 = new OrderHistoryDocument(
                    "647", "46789", "106", "Marble Chess Set",
                    200.0, 1, "6", "Diana Evans", "diana.evans@example.com", "987 Birch St",
                    now.minusDays(2), OrderHistory.OrderHistoryStatus.CANCELLED
            );

            OrderHistoryDocument order7 = new OrderHistoryDocument(
                    "7354", "6517", "107", "Electronic Chess Set",
                    300.0, 1, "7", "Eve Foster", "eve.foster@example.com", "321 Cedar St",
                    now.plusDays(1), OrderHistory.OrderHistoryStatus.CREATED
            );

            orderHistoryRepository.save(order1);
            orderHistoryRepository.save(order2);
            orderHistoryRepository.save(order3);
            orderHistoryRepository.save(order4);
            orderHistoryRepository.save(order5);
            orderHistoryRepository.save(order6);
            orderHistoryRepository.save(order7);

            System.out.println("✅ Demo order history data inserted with LocalDateTime (UTC).");
        };
    }
}
