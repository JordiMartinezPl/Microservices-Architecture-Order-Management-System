package cat.tecnocampus.customer.application.services;

import cat.tecnocampus.customer.adapters.in.restAPI.CustomerNotFoundException;
import cat.tecnocampus.customer.adapters.out.persistence.CustomerRepository;
import cat.tecnocampus.customer.application.ports.in.DeductCreditUseCase;
import cat.tecnocampus.customer.model.Customer;
import org.springframework.stereotype.Service;

public class DeductCreditService implements DeductCreditUseCase {
    private final CustomerRepository customerRepository;

    public DeductCreditService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }


    public boolean hasSufficientCredit(Long id, double amount) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return !(customer.getCreditBalance() < amount);
    }

    @Override
    public void deductCredit(Long id, double amount) {
        customerRepository.findById(id).ifPresent(customer -> {
                    if (0 < amount && hasSufficientCredit(id, amount)) {
                        customer.setCreditBalance(customer.getCreditBalance() - amount);
                        customerRepository.save(customer);
                    } else {
                        throw new IllegalArgumentException("The amount to deduct must be greater than 0");
                    }
                }
        );
    }

}
