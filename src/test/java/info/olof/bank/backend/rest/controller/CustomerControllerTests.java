package info.olof.bank.backend.rest.controller;

import info.olof.bank.backend.exception.ResourceNotFoundException;
import info.olof.bank.backend.model.entity.Customer;
import info.olof.bank.backend.rest.mapper.CustomerMapperImpl;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Import({
    CustomerMapperImpl.class
})
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTests {

    private static final Customer MOCK_CUSTOMER_SVEN = Customer
        .builder()
        .id(UUID.randomUUID())
        .firstName("Sven")
        .lastName("Svensson")
        .email("sven.svensson@gmail.com")
        .build();

    private static final Customer MOCK_CUSTOMER_LISA = Customer
        .builder()
        .id(UUID.randomUUID())
        .firstName("Lisa")
        .lastName("Svensson")
        .email("lisa.svensson@gmail.com")
        .build();

    private static final UUID CUSTOMER_NOT_FOUND = UUID.fromString("6512395f-d721-474d-86f3-57964a7b9c04");

    private static final String VALID_CREATE_USER_REQUEST_SVEN_JSON =
        "{"
            + "    \"firstName\": \"Sven\","
            + "    \"lastName\": \"Svensson\","
            + "    \"email\" : \"sven.svensson@gmail.com\","
            + "    \"dateOfBirth\" : \"1970-01-01\","
            + "    \"nationality\" : \"German\""
        + "}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Before
    public void setUp() {
        setUpMockData(customerService);
    }

    private void setUpMockData(CustomerService customerService) {
        Mockito.when(customerService.getCustomerById(eq(MOCK_CUSTOMER_SVEN.getId())))
            .thenReturn(MOCK_CUSTOMER_SVEN);

        Mockito.when(customerService.getCustomerById(eq(MOCK_CUSTOMER_LISA.getId())))
            .thenReturn(MOCK_CUSTOMER_LISA);

        Mockito.when(customerService.getCustomerById(eq(CUSTOMER_NOT_FOUND)))
            .thenThrow(new ResourceNotFoundException());

        Mockito.when(customerService.getCustomers())
            .thenReturn(Arrays.asList(MOCK_CUSTOMER_SVEN, MOCK_CUSTOMER_LISA));

        Mockito.when(customerService.addCustomer(any(Customer.class)))
            .thenReturn(MOCK_CUSTOMER_SVEN);
    }

    @Test
    public void givenGetCustomer_whenCustomerExist_thenReturnWith200AndCustomer() throws Exception {
        mockMvc.perform(get("/customers/" + MOCK_CUSTOMER_SVEN.getId())
            .contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.firstName", is(MOCK_CUSTOMER_SVEN.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(MOCK_CUSTOMER_SVEN.getLastName())))
            .andExpect(jsonPath("$.email", is(MOCK_CUSTOMER_SVEN.getEmail())))
            .andExpect(status().isOk());
    }

    @Test
    public void givenGetCustomer_whenCustomerDoesNotExist_thenReturnWith404() throws Exception {
        mockMvc.perform(get("/customers/" + CUSTOMER_NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isNotFound());
    }

    @Test
    public void givenGetCustomers_thenReturnWith200AndCustomers() throws Exception {
        mockMvc.perform(get("/customers")
            .contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.length()", is(2)))
            .andExpect(status().isOk());
    }

    @Test
    public void givenPostCustomer_thenReturnWith201AndCustomer() throws Exception {
        mockMvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(VALID_CREATE_USER_REQUEST_SVEN_JSON)
            .accept(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.firstName", is(MOCK_CUSTOMER_SVEN.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(MOCK_CUSTOMER_SVEN.getLastName())))
            .andExpect(jsonPath("$.email", is(MOCK_CUSTOMER_SVEN.getEmail())))
            .andExpect(status().isCreated());
    }

}
