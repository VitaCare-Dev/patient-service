package com.grupo10.patient_service.service;

import com.grupo10.patient_service.dto.DiseaseRequestDto;
import com.grupo10.patient_service.dto.DiseaseResponseDto;
import com.grupo10.patient_service.exception.ResourceNotFoundException;
import com.grupo10.patient_service.model.Disease;
import com.grupo10.patient_service.repository.DiseaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiseaseServiceTest {

    @Mock
    private DiseaseRepository diseaseRepository;

    @InjectMocks
    private DiseaseService diseaseService;

    private Disease buildDisease(Long id) {
        Disease d = new Disease();
        d.setIdEnfermedad(id);
        d.setNombreEnfermedad("Diabetes");
        d.setDescripcion("Enfermedad metabólica crónica");
        return d;
    }

    private DiseaseRequestDto buildRequest() {
        DiseaseRequestDto dto = new DiseaseRequestDto();
        dto.setNombreEnfermedad("Diabetes");
        dto.setDescripcion("Enfermedad metabólica crónica");
        return dto;
    }

    @Test
    void createDisease_success() {
        Disease saved = buildDisease(1L);
        when(diseaseRepository.save(any(Disease.class))).thenReturn(saved);

        DiseaseResponseDto result = diseaseService.createDisease(buildRequest());

        assertNotNull(result);
        assertEquals(1L, result.getIdEnfermedad());
        assertEquals("Diabetes", result.getNombreEnfermedad());
        assertEquals("Enfermedad metabólica crónica", result.getDescripcion());
        verify(diseaseRepository).save(any(Disease.class));
    }

    @Test
    void getDiseaseById_found() {
        when(diseaseRepository.findById(1L)).thenReturn(Optional.of(buildDisease(1L)));

        DiseaseResponseDto result = diseaseService.getDiseaseById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdEnfermedad());
    }

    @Test
    void getDiseaseById_notFound_throwsResourceNotFoundException() {
        when(diseaseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> diseaseService.getDiseaseById(99L));
    }

    @Test
    void getAllDiseases_returnsList() {
        when(diseaseRepository.findAll()).thenReturn(List.of(buildDisease(1L), buildDisease(2L)));

        List<DiseaseResponseDto> result = diseaseService.getAllDiseases();

        assertEquals(2, result.size());
    }

    @Test
    void getAllDiseases_emptyList() {
        when(diseaseRepository.findAll()).thenReturn(List.of());

        List<DiseaseResponseDto> result = diseaseService.getAllDiseases();

        assertTrue(result.isEmpty());
    }

    @Test
    void updateDisease_success() {
        Disease existing = buildDisease(1L);
        DiseaseRequestDto request = buildRequest();
        request.setNombreEnfermedad("Diabetes Tipo 2");

        when(diseaseRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(diseaseRepository.save(any(Disease.class))).thenReturn(existing);

        DiseaseResponseDto result = diseaseService.updateDisease(1L, request);

        assertNotNull(result);
        verify(diseaseRepository).save(existing);
    }

    @Test
    void updateDisease_notFound_throwsResourceNotFoundException() {
        when(diseaseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> diseaseService.updateDisease(99L, buildRequest()));
    }

    @Test
    void deleteDisease_success() {
        Disease disease = buildDisease(1L);
        when(diseaseRepository.findById(1L)).thenReturn(Optional.of(disease));

        diseaseService.deleteDisease(1L);

        verify(diseaseRepository).delete(disease);
    }

    @Test
    void deleteDisease_notFound_throwsResourceNotFoundException() {
        when(diseaseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> diseaseService.deleteDisease(99L));
    }
}
