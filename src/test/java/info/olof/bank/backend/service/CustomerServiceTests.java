package info.olof.bank.backend.service;

import info.olof.bank.backend.exception.ResourceNotFoundException;
import info.olof.bank.backend.model.entity.Customer;
import info.olof.bank.backend.model.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@Import({
    CustomerService.class
})
public class CustomerServiceTests {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    private final UUID CUSTOMER_NOT_FOUND = UUID.fromString("477f0c0d-d48e-40b7-9d85-910bf1af0a98");

    private final Customer MOCK_CUSTOMER_SVEN = Customer
        .builder()
        .id(UUID.fromString("659a14a7-a552-4a2c-9be6-63649ef38aaa"))
        .firstName("Sven")
        .lastName("Svensson")
        .email("sven.svensson@gmail.com")
        .build();

    @BeforeEach
    void setUp() {
        Mockito.when(customerRepository.findOneById(eq(MOCK_CUSTOMER_SVEN.getId())))
            .thenReturn(Optional.of(MOCK_CUSTOMER_SVEN));

        Mockito.when(customerRepository.findOneById(eq(CUSTOMER_NOT_FOUND)))
            .thenThrow(new ResourceNotFoundException());

        Mockito.when(customerRepository.findAll())
            .thenReturn(Collections.singletonList(MOCK_CUSTOMER_SVEN));

        Mockito.when(customerRepository.save(any(Customer.class)))
            .thenReturn(MOCK_CUSTOMER_SVEN);
    }

    @Test
    void givenGetCustomers_thenReturnCustomers() {
        List<Customer> customers = customerService.getCustomers();

        assertEquals(customers.size(), 1);
    }

    @Test
    void givenGetCustomerById_whenCustomerAvailable_thenReturnCustomer() {
        Customer customer = customerService.getCustomerById(MOCK_CUSTOMER_SVEN.getId());

        assertEquals(customer.getId(), MOCK_CUSTOMER_SVEN.getId());
        assertEquals(customer.getFirstName(), MOCK_CUSTOMER_SVEN.getFirstName());
        assertEquals(customer.getLastName(), MOCK_CUSTOMER_SVEN.getLastName());
        assertEquals(customer.getEmail(), MOCK_CUSTOMER_SVEN.getEmail());
    }

    @Test
    void givenGetCustomerById_whenCustomerNotAvailable_thenThrowError() {
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(CUSTOMER_NOT_FOUND));
    }

    @Test
    void givenAddCustomer_thenReturnCustomer() {
        Customer customer = customerService.addCustomer(MOCK_CUSTOMER_SVEN);

        assertEquals(customer.getId(), MOCK_CUSTOMER_SVEN.getId());
        assertEquals(customer.getFirstName(), MOCK_CUSTOMER_SVEN.getFirstName());
        assertEquals(customer.getLastName(), MOCK_CUSTOMER_SVEN.getLastName());
        assertEquals(customer.getEmail(), MOCK_CUSTOMER_SVEN.getEmail());
    }
}
