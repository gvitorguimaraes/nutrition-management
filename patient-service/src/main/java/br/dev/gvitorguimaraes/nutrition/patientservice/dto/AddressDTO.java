package br.dev.gvitorguimaraes.nutrition.patientservice.dto;

import jakarta.persistence.Column;

public record AddressDTO (
        String street,
        String city,
        String state,
        String zipCode,
        String country
){
}
