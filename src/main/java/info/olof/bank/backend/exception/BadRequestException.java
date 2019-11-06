package info.olof.bank.backend.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpRuntimeException {
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "Resource lookup failed";

    public BadRequestException() {
        super(STATUS, MESSAGE);
    }

    public BadRequestException(String message) {
        super(STATUS, message);
    }
}
