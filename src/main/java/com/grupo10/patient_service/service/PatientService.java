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

/**
 * Servicio de lógica de negocio para la gestión de pacientes.
 *
 * <p>Valida unicidad de RUT y de la relación usuario-paciente antes de persistir,
 * y realiza actualizaciones parciales ignorando campos nulos o en blanco.
 */
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    /**
     * Construye el servicio con el repositorio de pacientes inyectado.
     *
     * @param patientRepository repositorio JPA de pacientes
     */
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Crea y persiste un nuevo paciente tras validar que el usuario y el RUT no estén duplicados.
     *
     * @param request datos del paciente a registrar
     * @return DTO con los datos del paciente creado
     * @throws com.grupo10.patient_service.exception.UserNotFoundException   si {@code idUsuario} es nulo
     * @throws com.grupo10.patient_service.exception.DuplicateResourceException si el usuario o RUT ya existen
     */
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

    /**
     * Recupera un paciente por su identificador.
     *
     * @param id identificador del paciente
     * @return DTO con los datos del paciente
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si no se encuentra el paciente
     */
    public PatientResponseDto getPatientById(Long id) {
        Patient paciente = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.PATIENT_NOT_FOUND + id));
        return mapToResponse(paciente);
    }

    /**
     * Recupera todos los pacientes registrados en el sistema.
     *
     * @return lista de DTOs de pacientes; vacía si no hay registros
     */
    public List<PatientResponseDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Actualiza parcialmente los datos de un paciente existente.
     *
     * <p>Los campos nulos o en blanco en {@code request} son ignorados.
     * Los campos {@code idUsuario} y {@code rut} son protegidos y no pueden modificarse.
     *
     * @param id      identificador del paciente a actualizar
     * @param request campos con los nuevos valores
     * @return DTO con los datos actualizados del paciente
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si no se encuentra el paciente
     */
    public PatientResponseDto updatePatient(Long id, PatientRequestDto request) {
        Patient paciente = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.PATIENT_NOT_FOUND + id));

        UpdateUtil.copyNonNullProperties(request, paciente);

        Patient pacienteActualizado = patientRepository.save(paciente);
        return mapToResponse(pacienteActualizado);
    }

    /**
     * Elimina un paciente del sistema por su identificador.
     *
     * @param id identificador del paciente a eliminar
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si no se encuentra el paciente
     */
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
