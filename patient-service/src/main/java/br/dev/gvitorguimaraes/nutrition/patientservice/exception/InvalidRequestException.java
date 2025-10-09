package br.dev.gvitorguimaraes.nutrition.patientservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends RuntimeException {
    private HttpStatus status;

    public InvalidRequestException(String message) {
        super(message);
    }
    public InvalidRequestException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
