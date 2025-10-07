package br.dev.gvitorguimaraes.nutrition.patientservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

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
}
