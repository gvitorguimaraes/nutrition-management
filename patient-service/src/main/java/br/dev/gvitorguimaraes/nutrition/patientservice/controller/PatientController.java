package br.dev.gvitorguimaraes.nutrition.patientservice.controller;

import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientRequestDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientResponseDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.dto.validators.CreatePatientValidationGroup;
import br.dev.gvitorguimaraes.nutrition.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patient")
@Tag(name="Patient", description="API for managing patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }


    @GetMapping
    @Operation(summary = "Get patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponseDTO> createPatient(
                @Validated({Default.class, CreatePatientValidationGroup.class})
                @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO responseDTO = patientService.create(patientRequestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(
                @PathVariable UUID id,
                @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO responseDTO = patientService.update(id, patientRequestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
