package info.olof.bank.backend.config.exception;

import info.olof.bank.backend.exception.HttpRuntimeException;
import info.olof.bank.backend.generated.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpRuntimeException.class)
    public ResponseEntity<ErrorDTO> handleExceptionWithMessage(HttpRuntimeException exception) {

        LOGGER.error("Handling controller error {}", exception.getError());

        return ResponseEntity
            .status(exception.getError().getCode())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(exception.getError());
    }

    @ExceptionHandler({HttpServerErrorException.class, Exception.class})
    public ResponseEntity<Object> handleServerError(HttpServerErrorException exception) {

        LOGGER.error("Handling internal error {}", exception.getStatusText());

        return toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status, @NonNull WebRequest request) {
        LOGGER.error("Handling method argument not valid error");

        // Extract the field error information
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
            .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return toResponseEntity(status, errors);
    }

    private ResponseEntity<Object> toResponseEntity(HttpStatus status, Object body) {
        return ResponseEntity
            .status(status)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(body);
    }

}
