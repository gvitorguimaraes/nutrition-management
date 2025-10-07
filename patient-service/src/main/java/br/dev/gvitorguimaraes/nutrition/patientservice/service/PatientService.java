package br.dev.gvitorguimaraes.nutrition.patientservice.service;

import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientRequestDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientResponseDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.mapper.PatientMapper;
import br.dev.gvitorguimaraes.nutrition.patientservice.model.Patient;
import br.dev.gvitorguimaraes.nutrition.patientservice.repository.PatientRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    private PatientRepo repository;

    public PatientService(PatientRepo repository){
        this.repository = repository;
    }

    public List<PatientResponseDTO> getPatients(){
        return repository.findAll().stream()
                .map(patient -> PatientMapper.toDTO(patient)).toList();
    }

    public PatientResponseDTO create(PatientRequestDTO patientRequestDTO) {
        Patient newPatient = PatientMapper.toEntity(patientRequestDTO);
        newPatient.setRegisteredDate(LocalDate.now());
        repository.save(newPatient);

        return PatientMapper.toDTO(newPatient);
    }
}
