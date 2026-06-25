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

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    public DiseaseResponseDto createDisease(DiseaseRequestDto request) {
        Disease enfermedad = new Disease();
        enfermedad.setNombreEnfermedad(request.getNombreEnfermedad());
        enfermedad.setDescripcion(request.getDescripcion());

        Disease enfermedadGuardada = diseaseRepository.save(enfermedad);
        return mapToResponse(enfermedadGuardada);
    }

    public DiseaseResponseDto getDiseaseById(Long id) {
        Disease enfermedad = diseaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.DISEASE_NOT_FOUND + id));
        return mapToResponse(enfermedad);
    }

    public List<DiseaseResponseDto> getAllDiseases() {
        return diseaseRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public DiseaseResponseDto updateDisease(Long id, DiseaseRequestDto request) {
        Disease enfermedad = diseaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.DISEASE_NOT_FOUND + id));

        UpdateUtil.copyNonNullProperties(request, enfermedad);

        Disease enfermedadActualizada = diseaseRepository.save(enfermedad);
        return mapToResponse(enfermedadActualizada);
    }

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
