package info.olof.bank.backend.model.repository;

import info.olof.bank.backend.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findOneById(UUID id);

    Optional<Customer> findOneByEmail(String email);
}
