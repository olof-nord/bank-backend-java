package info.olof.bank.backend.rest.mapper;

import info.olof.bank.backend.model.entity.Customer;
import info.olof.bank.backend.rest.dto.customer.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerToCustomerDTOMapper implements Function<Customer, CustomerDTO> {

    @Override
    public CustomerDTO apply(Customer customer) {
        return CustomerDTO.builder()
            .firstName(customer.getFirstName())
            .lastName(customer.getLastName())
            .email(customer.getEmail())
            .build();
    }
}
