package cat.tecnocampus.customer.application.services;

import cat.tecnocampus.customer.adapters.in.restAPI.CustomerNotFoundException;
import cat.tecnocampus.customer.application.ports.in.GetCustomerUseCase;
import cat.tecnocampus.customer.application.ports.in.GetNameUseCase;
import cat.tecnocampus.customer.model.Customer;
import org.springframework.stereotype.Service;

public class GetNameService implements GetNameUseCase {

    private final cat.tecnocampus.customer.application.ports.out.CustomerRepository customerRepository;

    public GetNameService(cat.tecnocampus.customer.application.ports.out.CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    @Override
    public String getName(long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return customer.getName();
    }
}
