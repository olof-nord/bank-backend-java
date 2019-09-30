package info.olof.bank.backend.rest.controller;

import info.olof.bank.backend.model.entity.Customer;
import info.olof.bank.backend.rest.mapper.CustomerToCustomerResponseMapper;
import info.olof.bank.backend.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Import(CustomerToCustomerResponseMapper.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTests {

    private final static Customer MOCK_CUSTOMER_SVEN = Customer
        .builder()
        .id(UUID.randomUUID())
        .firstName("Sven")
        .lastName("Svensson")
        .email("sven.svensson@gmail.com")
        .build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Before
    public void setUp() {
        setUpMockData(customerService);
    }

    private void setUpMockData(CustomerService customerService) {
        Mockito.when(customerService.getCustomerByEmail(Mockito.eq(MOCK_CUSTOMER_SVEN.getEmail())))
            .thenReturn(Optional.of(MOCK_CUSTOMER_SVEN));
    }

    @Test
    public void givenGetCustomer_whenCustomerExist_thenReturnWith200AndCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/" + MOCK_CUSTOMER_SVEN.getEmail()))
            .andExpect(jsonPath("$.firstName", is(MOCK_CUSTOMER_SVEN.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(MOCK_CUSTOMER_SVEN.getLastName())))
            .andExpect(jsonPath("$.email", is(MOCK_CUSTOMER_SVEN.getEmail())))
            .andExpect(status().isOk());
    }

}
