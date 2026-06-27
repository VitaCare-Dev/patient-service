package com.grupo10.patient_service.service;

import com.grupo10.patient_service.constants.GlobalConstants;
import com.grupo10.patient_service.dto.DiseaseRequestDto;
import com.grupo10.patient_service.dto.DiseaseResponseDto;
import com.grupo10.patient_service.exception.ResourceNotFoundException;
import com.grupo10.patient_service.model.Disease;
import com.grupo10.patient_service.repository.DiseaseRepository;
import com.grupo10.patient_service.util.UpdateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de lógica de negocio para la gestión del catálogo de enfermedades crónicas.
 *
 * <p>Proporciona operaciones CRUD simples sin restricciones adicionales de negocio,
 * delegando la persistencia al repositorio correspondiente.
 */
@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    /**
     * Construye el servicio con el repositorio de enfermedades inyectado.
     *
     * @param diseaseRepository repositorio JPA de enfermedades
     */
    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    /**
     * Crea y persiste una nueva enfermedad en el catálogo.
     *
     * @param request datos de la enfermedad a registrar
     * @return DTO con los datos de la enfermedad creada
     */
    public DiseaseResponseDto createDisease(DiseaseRequestDto request) {
        Disease enfermedad = new Disease();
        enfermedad.setNombreEnfermedad(request.getNombreEnfermedad());
        enfermedad.setDescripcion(request.getDescripcion());

        Disease enfermedadGuardada = diseaseRepository.save(enfermedad);
        return mapToResponse(enfermedadGuardada);
    }

    /**
     * Recupera una enfermedad del catálogo por su identificador.
     *
     * @param id identificador de la enfermedad
     * @return DTO con los datos de la enfermedad
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si la enfermedad no existe
     */
    public DiseaseResponseDto getDiseaseById(Long id) {
        Disease enfermedad = diseaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.DISEASE_NOT_FOUND + id));
        return mapToResponse(enfermedad);
    }

    /**
     * Recupera todas las enfermedades registradas en el catálogo.
     *
     * @return lista de DTOs de enfermedades; vacía si no hay registros
     */
    public List<DiseaseResponseDto> getAllDiseases() {
        return diseaseRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Actualiza parcialmente los datos de una enfermedad existente.
     *
     * @param id      identificador de la enfermedad a actualizar
     * @param request campos con los nuevos valores
     * @return DTO con los datos actualizados de la enfermedad
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si la enfermedad no existe
     */
    public DiseaseResponseDto updateDisease(Long id, DiseaseRequestDto request) {
        Disease enfermedad = diseaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.DISEASE_NOT_FOUND + id));

        UpdateUtil.copyNonNullProperties(request, enfermedad);

        Disease enfermedadActualizada = diseaseRepository.save(enfermedad);
        return mapToResponse(enfermedadActualizada);
    }

    /**
     * Elimina una enfermedad del catálogo por su identificador.
     *
     * @param id identificador de la enfermedad a eliminar
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si la enfermedad no existe
     */
    public void deleteDisease(Long id) {
        Disease enfermedad = diseaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.DISEASE_NOT_FOUND + id));
        diseaseRepository.delete(enfermedad);
    }

    private DiseaseResponseDto mapToResponse(Disease enfermedad) {
        DiseaseResponseDto response = new DiseaseResponseDto();
        response.setIdEnfermedad(enfermedad.getIdEnfermedad());
        response.setNombreEnfermedad(enfermedad.getNombreEnfermedad());
        response.setDescripcion(enfermedad.getDescripcion());
        return response;
    }
}
