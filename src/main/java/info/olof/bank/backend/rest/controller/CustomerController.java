package info.olof.bank.backend.rest.controller;

import info.olof.bank.backend.generated.dto.CustomerDTO;
import info.olof.bank.backend.rest.mapper.CustomerMapperImpl;
import info.olof.bank.backend.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final CustomerMapperImpl customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapperImpl customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {

        LOGGER.info("getCustomers request");

        return ResponseEntity.ok()
            .body(customerService.getCustomers().stream()
            .map(customerMapper::customerToCustomerDTO)
            .collect(Collectors.toList()));
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody @Valid CustomerDTO request) {

        LOGGER.info("addCustomer request: {}", request.toString());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(customerMapper.customerToCustomerDTO(
                customerService.addCustomer(customerMapper.customerDTOToCustomer(request)))
            );
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String customerId) {

        LOGGER.info("getCustomer request: customerId: {}", customerId);

        return ResponseEntity.ok()
            .body(customerMapper.customerToCustomerDTO(
                customerService.getCustomerById(UUID.fromString(customerId))));
    }

}
