package info.olof.bank.backend.rest.controller;

import info.olof.bank.backend.model.entity.Customer;
import info.olof.bank.backend.rest.mapper.CustomerDTOToCustomerMapper;
import info.olof.bank.backend.rest.mapper.CustomerToCustomerDTOMapper;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Import({
    CustomerToCustomerDTOMapper.class,
    CustomerDTOToCustomerMapper.class
})
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTests {

    private final static Customer MOCK_CUSTOMER_SVEN = Customer
        .builder()
        .id(UUID.randomUUID())
        .firstName("Sven")
        .lastName("Svensson")
        .email("sven.svensson@gmail.com")
        .build();

    private final static Customer MOCK_CUSTOMER_LISA = Customer
        .builder()
        .id(UUID.randomUUID())
        .firstName("Lisa")
        .lastName("Svensson")
        .email("lisa.svensson@gmail.com")
        .build();


    private final static String VALID_CREATE_USER_REQUEST_SVEN_JSON =
        "{"
            + "    \"firstName\": \"Sven\","
            + "    \"lastName\": \"Svensson\","
            + "    \"email\" : \"sven.svensson@gmail.com\""
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
        Mockito.when(customerService.getCustomerByEmail(Mockito.eq(MOCK_CUSTOMER_SVEN.getEmail())))
            .thenReturn(Optional.of(MOCK_CUSTOMER_SVEN));

        Mockito.when(customerService.getCustomerByEmail(Mockito.eq(MOCK_CUSTOMER_LISA.getEmail())))
            .thenReturn(Optional.of(MOCK_CUSTOMER_LISA));

        Mockito.when(customerService.getCustomers())
            .thenReturn(Arrays.asList(MOCK_CUSTOMER_SVEN, MOCK_CUSTOMER_LISA));

        Mockito.when(customerService.addCustomer(Mockito.any(Customer.class)))
            .thenReturn(MOCK_CUSTOMER_SVEN);
    }

    @Test
    public void givenGetCustomer_whenCustomerExist_thenReturnWith200AndCustomer() throws Exception {
        mockMvc.perform(get("/customers/" + MOCK_CUSTOMER_SVEN.getEmail())
            .contentType(MediaType.APPLICATION_JSON))

            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.firstName", is(MOCK_CUSTOMER_SVEN.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(MOCK_CUSTOMER_SVEN.getLastName())))
            .andExpect(jsonPath("$.email", is(MOCK_CUSTOMER_SVEN.getEmail())))
            .andExpect(status().isOk());
    }

    @Test
    public void givenGetCustomer_whenCustomerDoesNotExist_thenReturnWith404() throws Exception {
        mockMvc.perform(get("/customers/" + "not-exist")
            .contentType(MediaType.APPLICATION_JSON))

            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void givenGetCustomers_thenReturnWith200AndCustomers() throws Exception {
        mockMvc.perform(get("/customers")
            .contentType(MediaType.APPLICATION_JSON))

            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.length()", is(2)))
            .andExpect(status().isOk());
    }

    @Test
    public void givenPostCustomer_thenReturnWith201AndCustomer() throws Exception {
        mockMvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(VALID_CREATE_USER_REQUEST_SVEN_JSON)
            .accept(MediaType.APPLICATION_JSON))

            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.firstName", is(MOCK_CUSTOMER_SVEN.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(MOCK_CUSTOMER_SVEN.getLastName())))
            .andExpect(jsonPath("$.email", is(MOCK_CUSTOMER_SVEN.getEmail())))
            .andExpect(status().isCreated());
    }

}
