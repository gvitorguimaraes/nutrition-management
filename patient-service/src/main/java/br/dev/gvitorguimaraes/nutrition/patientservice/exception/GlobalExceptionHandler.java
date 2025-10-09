package br.dev.gvitorguimaraes.nutrition.patientservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultRestError> handleValidationException(
            HttpServletRequest request,
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(
                new DefaultRestError(request.getRequestURI(), ex.getStatusCode().value(), errors));
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<DefaultRestError> handleInvalidRequestException(
            HttpServletRequest request,
            InvalidRequestException ex){

        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status.value()).body(
                new DefaultRestError(request.getRequestURI(), status.value(), Map.of("message", ex.getMessage()))
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<DefaultRestError> handleHttpMethodNotSupportedException(
            HttpServletRequest request,
            Exception ex) {

        Map<String, String> body = Map.of("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new DefaultRestError(request.getRequestURI(), HttpStatus.METHOD_NOT_ALLOWED.value(), body)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultRestError> handleGenericException(
            HttpServletRequest request,
            Exception ex) {

        log.warn("Internal Error", ex);
        Map<String, String> body = Map.of("message", "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new DefaultRestError(request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value(), body)
        );
    }
}
