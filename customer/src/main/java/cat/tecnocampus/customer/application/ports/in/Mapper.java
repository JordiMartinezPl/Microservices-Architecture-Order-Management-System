package cat.tecnocampus.customer.application.ports.in;

import cat.tecnocampus.customer.model.Customer;

public class Mapper {
    public static CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getCreditBalance());
    }
}
