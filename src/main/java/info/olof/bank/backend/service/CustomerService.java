package info.olof.bank.backend.service;

import info.olof.bank.backend.exception.ResourceNotFoundException;
import info.olof.bank.backend.model.entity.Customer;
import info.olof.bank.backend.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id)
            .orElseThrow(ResourceNotFoundException::new);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findOneByEmail(email)
            .orElseThrow(ResourceNotFoundException::new);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
