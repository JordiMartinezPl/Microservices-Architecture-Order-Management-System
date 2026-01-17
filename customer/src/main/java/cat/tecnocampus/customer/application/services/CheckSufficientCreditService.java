package cat.tecnocampus.customer.application.services;

import cat.tecnocampus.customer.adapters.in.restAPI.CustomerNotFoundException;
import cat.tecnocampus.customer.model.Customer;
import org.springframework.stereotype.Service;

public class CheckSufficientCreditService implements cat.tecnocampus.customer.application.ports.in.CheckSufficientCreditUseCase {
    private final cat.tecnocampus.customer.application.ports.out.CustomerRepository customerRepository;

    public CheckSufficientCreditService(cat.tecnocampus.customer.application.ports.out.CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }


    @Override
    public boolean hasSufficientCredit(Long id, double amount) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return !(customer.getCreditBalance() < amount);
    }

}
