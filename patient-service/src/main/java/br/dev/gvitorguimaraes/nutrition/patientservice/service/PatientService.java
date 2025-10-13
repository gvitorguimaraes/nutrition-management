package br.dev.gvitorguimaraes.nutrition.patientservice.service;

import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientRequestDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.dto.PatientResponseDTO;
import br.dev.gvitorguimaraes.nutrition.patientservice.exception.InvalidRequestException;
import br.dev.gvitorguimaraes.nutrition.patientservice.grpc.BillingServiceGrpcClient;
import br.dev.gvitorguimaraes.nutrition.patientservice.kafka.KafkaProducer;
import br.dev.gvitorguimaraes.nutrition.patientservice.mapper.PatientMapper;
import br.dev.gvitorguimaraes.nutrition.patientservice.model.Address;
import br.dev.gvitorguimaraes.nutrition.patientservice.model.Patient;
import br.dev.gvitorguimaraes.nutrition.patientservice.repository.PatientRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepo repository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepo repository,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer){

        this.repository = repository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getPatients(){
        return repository.findAll().stream()
                .map(PatientMapper::toDTO).toList();
    }

    private void checkEmailAlreadyExists(String email) throws InvalidRequestException {
        if (repository.existsByEmail(email)) {
            throw new InvalidRequestException("A patient with this email already exists");
        }
    }

    public PatientResponseDTO create(PatientRequestDTO patientRequestDTO) {
        checkEmailAlreadyExists(patientRequestDTO.email());
        Patient newPatient = PatientMapper.toEntity(patientRequestDTO);
        newPatient.setRegisteredDate(LocalDate.now());
        repository.save(newPatient);

        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail()
        );

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO update(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = repository.findById(id).orElseThrow(
                () -> new InvalidRequestException("User not found with ID: "+id.toString()));
        if (!patient.getEmail().equals(patientRequestDTO.email())) {
            checkEmailAlreadyExists(patientRequestDTO.email());
        }

        patient.setName(patientRequestDTO.name());
        patient.setEmail(patientRequestDTO.email());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.dateOfBirth()));

        if (patientRequestDTO.address() != null) {
            if (patient.getAddress() == null) {
                patient.setAddress(new Address());
            }
            patient.getAddress().setStreet(patientRequestDTO.address().street());
            patient.getAddress().setCity(patientRequestDTO.address().city());
            patient.getAddress().setState(patientRequestDTO.address().state());
            patient.getAddress().setCountry(patientRequestDTO.address().country());
            patient.getAddress().setZipCode(patientRequestDTO.address().zipCode());
        }

        patient = repository.save(patient);
        return PatientMapper.toDTO(patient);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
