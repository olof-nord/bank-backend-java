package info.olof.bank.backend.rest.controller;

import info.olof.bank.backend.rest.dto.customer.CustomerResponse;
import info.olof.bank.backend.rest.mapper.CustomerToCustomerResponseMapper;
import info.olof.bank.backend.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerToCustomerResponseMapper customerToCustomerResponseMapper;

    public CustomerController(CustomerService customerService, CustomerToCustomerResponseMapper customerToCustomerResponseMapper) {
        this.customerService = customerService;
        this.customerToCustomerResponseMapper = customerToCustomerResponseMapper;
    }

    @GetMapping("/customers/{email}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String email) {

        return customerService.getCustomerByEmail(email)
            .map(customer -> ResponseEntity.ok().body(customerToCustomerResponseMapper.apply(customer)))
            .orElse(ResponseEntity.of(Optional.of(CustomerResponse.builder().build())));
    }

}
