package info.olof.bank.backend.rest.mapper;

import info.olof.bank.backend.generated.dto.CustomerDTO;
import info.olof.bank.backend.model.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@Import({
    CustomerMapperImpl.class
})
public class CustomerMapperTests {

    private static UUID UUID_SVEN = UUID.randomUUID();

    private Customer customer = Customer
        .builder()
        .id(UUID_SVEN)
        .firstName("Sven")
        .lastName("Svensson")
        .email("sven.svensson@gmail.com")
        .build();

    private CustomerDTO customerDTO = new CustomerDTO();

    @Autowired
    private CustomerMapperImpl customerMapper;

    @Before
    public void setUp() {
        customerDTO.setId(UUID_SVEN);
        customerDTO.setFirstName("Sven");
        customerDTO.setLastName("Svensson");
        customerDTO.setEmail("sven.svensson@gmail.com");
    }

    @Test
    public void givenMapCustomerDTO_thenMapAllFields() {
        Customer sut = customerMapper.customerDTOToCustomer(customerDTO);

        assertEquals(sut.getId(), customerDTO.getId());
        assertEquals(sut.getFirstName(), customerDTO.getFirstName());
        assertEquals(sut.getLastName(), customerDTO.getLastName());
        assertEquals(sut.getEmail(), customerDTO.getEmail());

    }

    @Test
    public void givenMapCustomer_thenMapAllFields() {
        CustomerDTO sut = customerMapper.customerToCustomerDTO(customer);

        assertEquals(sut.getId(), customerDTO.getId());
        assertEquals(sut.getFirstName(), customerDTO.getFirstName());
        assertEquals(sut.getLastName(), customerDTO.getLastName());
        assertEquals(sut.getEmail(), customerDTO.getEmail());

    }
}
