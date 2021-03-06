package info.olof.bank.backend.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private LocalDateTime created;

    private String alias;

    private String streetAndNumber;

    private String additionalInformation;

    private Integer postcode;

    private String city;

    private String country;

    @ManyToOne
    private Customer customer;

    @PrePersist
    void createdAt() {
        this.created = LocalDateTime.now();
    }

}
