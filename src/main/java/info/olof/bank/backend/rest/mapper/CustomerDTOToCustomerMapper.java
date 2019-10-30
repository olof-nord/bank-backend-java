package info.olof.bank.backend.rest.mapper;

import info.olof.bank.backend.model.entity.Customer;
import info.olof.bank.backend.generated.dto.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
public class CustomerDTOToCustomerMapper implements Function<CustomerDTO, Customer> {

    @Override
    public Customer apply(CustomerDTO customerDTO) {
        return Customer.builder()
            .id(UUID.randomUUID())
            .firstName(customerDTO.getFirstName())
            .lastName(customerDTO.getLastName())
            .email(customerDTO.getEmail())
            .build();
    }
}
