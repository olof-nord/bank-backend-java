package info.olof.bank.backend.rest.controller;

import info.olof.bank.backend.rest.dto.customer.CustomerDTO;
import info.olof.bank.backend.rest.mapper.CustomerDTOToCustomerMapper;
import info.olof.bank.backend.rest.mapper.CustomerToCustomerDTOMapper;
import info.olof.bank.backend.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerToCustomerDTOMapper customerToCustomerDTOMapper;
    private final CustomerDTOToCustomerMapper customerDTOToCustomerMapper;

    public CustomerController(CustomerService customerService, CustomerToCustomerDTOMapper customerToCustomerDTOMapper, CustomerDTOToCustomerMapper customerDTOToCustomerMapper) {
        this.customerService = customerService;
        this.customerToCustomerDTOMapper = customerToCustomerDTOMapper;
        this.customerDTOToCustomerMapper = customerDTOToCustomerMapper;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {

        return ResponseEntity.ok().body(customerService.getCustomers().stream()
            .map(customerToCustomerDTOMapper)
            .collect(Collectors.toList()));
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody @Valid CustomerDTO customerRequest) {

        return ResponseEntity.ok().body(customerToCustomerDTOMapper.apply(
            customerService.addCustomer(customerDTOToCustomerMapper.apply(customerRequest))
        ));
    }

    @GetMapping("/customers/{email}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String email) {

        return customerService.getCustomerByEmail(email)
            .map(customer -> ResponseEntity.ok().body(customerToCustomerDTOMapper.apply(customer)))
            .orElse(ResponseEntity.of(Optional.of(CustomerDTO.builder().build())));
    }

}
