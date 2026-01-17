package cat.tecnocampus.customer.adapters.out;

import cat.tecnocampus.customer.adapters.out.persistence.CustomerRepository;
import cat.tecnocampus.customer.model.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerSeeder {

    @Bean
    CommandLineRunner initCustomerDatabase(CustomerRepository customerRepository) {
        return args -> {
            // Insert demo customers
            Customer customer1 = new Customer("John Doe", "john.doe@example.com", "123 Main St", 500.0);
            Customer customer2 = new Customer("Jane Smith", "jane.smith@example.com", "456 Oak St", 750.0);
            Customer customer3 = new Customer("Alice Johnson", "alice.johnson@example.com", "789 Pine St", 1000.0);

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);

            System.out.println("✅ 3 Demo customers inserted.");
        };
    }
}