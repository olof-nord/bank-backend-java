package info.olof.bank.backend.rest.mapper;

import info.olof.bank.backend.generated.dto.CustomerDTO;
import info.olof.bank.backend.model.entity.Customer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import({
    CustomerMapperImpl.class
})
public class CustomerMapperTests {

    @Autowired
    private CustomerMapperImpl customerMapper;

    @SuppressWarnings("unused")
    private static Stream<Arguments> provideTestCustomers() {
        return Stream.of(
            Arguments.of(Customer.builder()
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .firstName("Sven")
                .lastName("Svensson")
                .email("sven.svensson@gmail.com")
                .dateOfBirth(LocalDate.of(1970, 1, 1))
                .nationality("German")
                .build()
            ),
            Arguments.of(Customer.builder()
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .firstName("Lisa")
                .lastName("Svensson")
                .email("lisa.svensson@gmail.com")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .nationality("Swedish")
                .build()
            )
        );
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> provideTestCustomerDTOs() {
        return Stream.of(
            Arguments.of(new CustomerDTO()
                .firstName("Sven")
                .lastName("Svensson")
                .email("sven.svensson@gmail.com")
                .dateOfBirth(LocalDate.of(1970, 1, 1))
                .nationality("German")
            ),
            Arguments.of(new CustomerDTO()
                .firstName("Lisa")
                .lastName("Svensson")
                .email("lisa.svensson@gmail.com")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestCustomers")
    void givenCustomer_thenMap_CustomerDTO(Customer customer) {

        CustomerDTO dto = customerMapper.customerToCustomerDTO(customer);

        assertEquals(dto.getId(), customer.getId());
        assertEquals(dto.getFirstName(), customer.getFirstName());
        assertEquals(dto.getLastName(), customer.getLastName());
        assertEquals(dto.getEmail(), customer.getEmail());
        assertEquals(dto.getDateOfBirth(), customer.getDateOfBirth());
        assertEquals(dto.getNationality(), customer.getNationality());
    }

    @ParameterizedTest
    @MethodSource("provideTestCustomerDTOs")
    void givenCustomerDTO_thenMap_Customer(CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        assertEquals(customer.getId(), customerDTO.getId());
        assertEquals(customer.getFirstName(), customerDTO.getFirstName());
        assertEquals(customer.getLastName(), customerDTO.getLastName());
        assertEquals(customer.getEmail(), customerDTO.getEmail());
        assertEquals(customer.getDateOfBirth(), customerDTO.getDateOfBirth());
        assertEquals(customer.getNationality(), customerDTO.getNationality());
    }
}
