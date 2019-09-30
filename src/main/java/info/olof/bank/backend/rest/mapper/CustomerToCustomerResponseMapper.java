package info.olof.bank.backend.rest.mapper;

import info.olof.bank.backend.model.entity.Customer;
import info.olof.bank.backend.rest.dto.customer.CustomerResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerToCustomerResponseMapper implements Function<Customer, CustomerResponse> {

    @Override
    public CustomerResponse apply(Customer customer) {
        return CustomerResponse.builder()
            .firstName(customer.getFirstName())
            .lastName(customer.getLastName())
            .email(customer.getEmail())
            .build();
    }
}
