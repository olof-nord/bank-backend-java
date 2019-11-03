package info.olof.bank.backend.rest.mapper;

import info.olof.bank.backend.generated.dto.CustomerDTO;
import info.olof.bank.backend.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer customerDTOToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
