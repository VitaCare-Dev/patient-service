package com.grupo10.patient_service.service;

import com.grupo10.patient_service.exception.DuplicateResourceException;
import com.grupo10.patient_service.exception.ResourceNotFoundException;
import com.grupo10.patient_service.model.Patient;
import com.grupo10.patient_service.dto.PatientRequestDto;
import com.grupo10.patient_service.dto.PatientResponseDto;
import com.grupo10.patient_service.repository.PatientRepository;

public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponseDto crearPaciente(PatientRequestDto request) {
        if (patientRepository.existsByRut(request.getRut())) {
            throw new DuplicateResourceException("Ya existe un paciente registrado con el RUT ingresado");
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

        return mapearAResponse(pacienteGuardado);
    }

    public PatientResponseDto obtenerPacientePorId(Long id) {
        Patient paciente = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con el ID: " + id));
        return mapearAResponse(paciente);
    }

    private PatientResponseDto mapearAResponse(Patient paciente) {
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
