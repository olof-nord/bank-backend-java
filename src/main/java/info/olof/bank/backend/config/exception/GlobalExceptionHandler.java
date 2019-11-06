package info.olof.bank.backend.config.exception;

import info.olof.bank.backend.exception.HttpRuntimeException;
import info.olof.bank.backend.generated.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRuntimeException.class)
    public ResponseEntity<ErrorDTO> handleExceptionWithMessage(HttpRuntimeException exception) {

        return ResponseEntity
            .status(exception.getError().getCode())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(exception.getError());
    }

}
