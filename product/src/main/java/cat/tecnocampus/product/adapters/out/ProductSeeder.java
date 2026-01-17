package cat.tecnocampus.product.adapters.out;


import cat.tecnocampus.product.adapters.out.persistence.ProductRepository;
import cat.tecnocampus.product.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductSeeder {
    @Bean
    CommandLineRunner initCustomerDatabase(ProductRepository productRepository) {
        return args -> {
            Product product1 = new Product("Chess Board Classic", "Wooden chess board", 49.99, 100);
            Product product2 = new Product("Metal Chess Set", "Luxury chess set with metal pieces", 120.0, 50);
            Product product3 = new Product("Plastic Chess Set", "Affordable plastic chess set", 20.0, 200);
            Product product4 = new Product("Plastic Chess Set", "Affordable plastic chess set", 22000.0, 200);


            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            System.out.println("✅ 3 Demo products inserted.");
        };
    }
}
