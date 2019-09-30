package info.olof.bank.backend.rest.dto.customer;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class CreateCustomerRequest {

    @NotNull
    @NotBlank
    @Size(max = 255, message = "First name must not be longer than 255 characters.")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 255, message = "Last name must not be longer than 255 characters.")
    private String lastName;

    @NotNull
    @NotBlank
    @Size(max = 100, message = "Email must not be longer than 100 characters.")
    private String email;
}
