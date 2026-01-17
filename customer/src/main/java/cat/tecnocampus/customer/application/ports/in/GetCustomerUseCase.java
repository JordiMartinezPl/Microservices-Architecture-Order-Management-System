package cat.tecnocampus.customer.application.ports.in;

import java.util.Optional;

public interface GetCustomerUseCase {
    Optional<CustomerResponse> getCustomer(Long id);


}
