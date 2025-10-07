package br.dev.gvitorguimaraes.nutrition.patientservice.controller;

import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientRequestDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientResponseDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
                @Valid @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO responseDTO = patientService.create(patientRequestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }
}
