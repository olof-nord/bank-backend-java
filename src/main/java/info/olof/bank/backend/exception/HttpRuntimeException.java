package info.olof.bank.backend.exception;

import info.olof.bank.backend.generated.dto.ErrorDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class HttpRuntimeException extends RuntimeException {
    private ErrorDTO error = new ErrorDTO();

    HttpRuntimeException(HttpStatus status, String message) {
        this.error.setCode(status.value());
        this.error.setMessage(message);
    }

}
