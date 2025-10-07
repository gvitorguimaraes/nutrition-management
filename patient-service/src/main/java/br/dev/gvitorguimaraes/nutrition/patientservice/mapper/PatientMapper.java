package br.dev.gvitorguimaraes.nutrition.patientservice.mapper;

import br.dev.gvitorguimaraes.nutrition.patientservice.dto.AddressDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientRequestDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientResponseDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.model.Address;
import br.dev.gvitorguimaraes.nutrition.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient) {

        AddressDTO addressDTO = null;
        if (patient.getAddress() != null) {
            addressDTO = new AddressDTO(patient.getAddress().getStreet(),
                                        patient.getAddress().getCity(),
                                        patient.getAddress().getState(),
                                        patient.getAddress().getZipCode(),
                                        patient.getAddress().getCountry()
                        );
        }

        return new PatientResponseDTO(
                        patient.getId().toString(),
                        patient.getName(),
                        patient.getEmail(),
                        addressDTO,
                        patient.getDateOfBirth().toString()
        );
    }

    public static Patient toEntity(PatientRequestDTO dto){
        Patient patient = new Patient();
        patient.setName(dto.name());
        patient.setEmail(dto.email());

        if (dto.address() != null){
            Address address = new Address();
            address.setCity(dto.address().city());
            address.setStreet(dto.address().street());
            address.setState(dto.address().state());
            address.setCountry(dto.address().country());
            address.setZipCode(dto.address().zipCode());
            patient.setAddress(address);
        }

        patient.setDateOfBirth(LocalDate.parse(dto.dateOfBirth()));

        return patient;
    }
}
