package br.dev.gvitorguimaraes.nutrition.patientservice.exception;

import java.util.HashMap;
import java.util.Map;

public class DefaultRestError {

    private String endpoint;
    private Integer statusCode;
    private Map<String, String> errors;

    public DefaultRestError(){}

    public DefaultRestError(String endpoint, Integer statusCode, Map<String, String> errors){
        this.endpoint = endpoint;
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getErrors() {
        if (errors == null) errors = new HashMap<>();
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
