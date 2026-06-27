package com.grupo10.patient_service.service;

import com.grupo10.patient_service.dto.PatientRequestDto;
import com.grupo10.patient_service.dto.PatientResponseDto;
import com.grupo10.patient_service.exception.DuplicateResourceException;
import com.grupo10.patient_service.exception.ResourceNotFoundException;
import com.grupo10.patient_service.exception.UserNotFoundException;
import com.grupo10.patient_service.model.Patient;
import com.grupo10.patient_service.repository.PatientRepository;
import com.grupo10.patient_service.util.UpdateUtil;
import org.springframework.stereotype.Service;
import com.grupo10.patient_service.constants.GlobalConstants;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponseDto createPatient(PatientRequestDto request) {
        if (request.getIdUsuario() == null) {
            throw new UserNotFoundException(GlobalConstants.USER_NOT_FOUND + "null");
        }
        if (patientRepository.existsByIdUsuario(request.getIdUsuario())) {
            throw new DuplicateResourceException(GlobalConstants.DUPLICATE_USER_PATIENT);
        }
        if (patientRepository.existsByRut(request.getRut())) {
            throw new DuplicateResourceException(GlobalConstants.DUPLICATE_RUT);
        }

        Patient paciente = new Patient();
        paciente.setIdUsuario(request.getIdUsuario());
        paciente.setRut(request.getRut());
        paciente.setNombre(request.getNombre());
        paciente.setApellidoPaterno(request.getApellidoPaterno());
        paciente.setApellidoMaterno(request.getApellidoMaterno());
        paciente.setFechaNacimiento(request.getFechaNacimiento());
        paciente.setTelefonoPrincipal(request.getTelefonoPrincipal());
        paciente.setTelefonoSecundario(request.getTelefonoSecundario());

        Patient pacienteGuardado = patientRepository.save(paciente);
        return mapToResponse(pacienteGuardado);
    }

    public PatientResponseDto getPatientById(Long id) {
        Patient paciente = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.PATIENT_NOT_FOUND + id));
        return mapToResponse(paciente);
    }

    public List<PatientResponseDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PatientResponseDto updatePatient(Long id, PatientRequestDto request) {
        Patient paciente = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.PATIENT_NOT_FOUND + id));

        UpdateUtil.copyNonNullProperties(request, paciente);

        Patient pacienteActualizado = patientRepository.save(paciente);
        return mapToResponse(pacienteActualizado);
    }

    public void deletePatient(Long id) {
        Patient paciente = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.PATIENT_NOT_FOUND + id));
        patientRepository.delete(paciente);
    }

    private PatientResponseDto mapToResponse(Patient paciente) {
        PatientResponseDto response = new PatientResponseDto();
        response.setIdPaciente(paciente.getId());
        response.setIdUsuario(paciente.getIdUsuario());
        response.setRut(paciente.getRut());
        response.setNombre(paciente.getNombre());
        response.setApellidoPaterno(paciente.getApellidoPaterno());
        response.setApellidoMaterno(paciente.getApellidoMaterno());
        response.setFechaNacimiento(paciente.getFechaNacimiento());
        response.setTelefonoPrincipal(paciente.getTelefonoPrincipal());
        response.setTelefonoSecundario(paciente.getTelefonoSecundario());
        return response;
    }
}
