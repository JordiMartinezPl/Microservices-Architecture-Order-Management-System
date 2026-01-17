package cat.tecnocampus.customer.adapters.out.persistence;

import cat.tecnocampus.customer.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepository implements cat.tecnocampus.customer.application.ports.out.CustomerRepository {
    private final CustomerJPARepository customerJPARepository;

    public CustomerRepository(CustomerJPARepository customerJPARepository) {
        this.customerJPARepository = customerJPARepository;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerJPARepository.findById(id).map(CustomerMapper::toModel);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = CustomerMapper.toEntity(customer);
        return CustomerMapper.toModel(customerJPARepository.save(entity));
    }
}
