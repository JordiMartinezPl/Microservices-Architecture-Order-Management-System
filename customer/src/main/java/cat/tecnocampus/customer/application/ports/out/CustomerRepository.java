package cat.tecnocampus.customer.application.ports.out;

import cat.tecnocampus.customer.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);

    Customer save(Customer customer);
}
