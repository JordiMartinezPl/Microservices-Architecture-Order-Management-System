package cat.tecnocampus.customer.adapters.out.persistence;

import cat.tecnocampus.customer.model.Customer;

public class CustomerMapper {

    public static CustomerEntity toEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setAddress(customer.getAddress());
        entity.setCreditBalance(customer.getCreditBalance());
        return entity;
    }

    public static Customer toModel(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setName(entity.getName());
        customer.setEmail(entity.getEmail());
        customer.setAddress(entity.getAddress());
        customer.setCreditBalance(entity.getCreditBalance());
        return customer;
    }
}
