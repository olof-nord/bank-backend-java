package info.olof.bank.backend.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private LocalDateTime created;

    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate dateOfBirth;

    private String nationality;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Address> addresses;

    @PrePersist
    private void createdAt() {
        this.created = LocalDateTime.now();
    }
}
