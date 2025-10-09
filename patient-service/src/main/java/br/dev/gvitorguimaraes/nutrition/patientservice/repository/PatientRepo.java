package br.dev.gvitorguimaraes.nutrition.patientservice.repository;

import br.dev.gvitorguimaraes.nutrition.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepo extends JpaRepository<Patient, UUID> {
    Boolean existsByEmail(String email);
}
