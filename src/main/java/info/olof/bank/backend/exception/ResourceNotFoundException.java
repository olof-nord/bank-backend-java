package info.olof.bank.backend.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends HttpRuntimeException {
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "Resource not found";

    public ResourceNotFoundException() {
        super(STATUS, MESSAGE);
    }

    public ResourceNotFoundException(String message) {
        super(STATUS, message);
    }
}
