package cat.tecnocampus.customer.application.services;

import cat.tecnocampus.customer.application.ports.in.CustomerResponse;
import cat.tecnocampus.customer.application.ports.in.GetCustomerUseCase;
import cat.tecnocampus.customer.application.ports.in.Mapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


public class GetCustomerService implements GetCustomerUseCase {

    private final cat.tecnocampus.customer.application.ports.out.CustomerRepository customerRepository;

    public GetCustomerService(cat.tecnocampus.customer.application.ports.out.CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<CustomerResponse> getCustomer(Long id) {
        return customerRepository.findById(id).map(Mapper::toCustomerResponse);
    }
}
