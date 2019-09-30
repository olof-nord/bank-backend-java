package info.olof.bank.backend.rest.dto.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {

    private String firstName;
    private String lastName;
    private String email;

}
