package info.olof.bank.backend.config.exception;

import info.olof.bank.backend.exception.BadRequestException;
import info.olof.bank.backend.exception.HttpRuntimeException;
import info.olof.bank.backend.generated.dto.ErrorDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpRuntimeException.class)
    public ResponseEntity<ErrorDTO> handleExceptionWithMessage(HttpRuntimeException exception) {

        return ResponseEntity
            .status(exception.getError().getCode())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(exception.getError());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException e) {
        BadRequestException exception = new BadRequestException();

        LOGGER.error(e.getConstraintName());

        return ResponseEntity
            .status(exception.getError().getCode())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(exception.getError());
    }

}
