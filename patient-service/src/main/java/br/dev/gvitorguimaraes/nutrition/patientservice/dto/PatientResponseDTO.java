package br.dev.gvitorguimaraes.nutrition.patientservice.dto;

public record PatientResponseDTO (
        String id,
        String name,
        String email,
        AddressDTO address,
        String dateOfBirth
) {
}
