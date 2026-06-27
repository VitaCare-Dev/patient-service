package com.grupo10.patient_service.controller;

import com.grupo10.patient_service.dto.DiseaseRequestDto;
import com.grupo10.patient_service.dto.DiseaseResponseDto;
import com.grupo10.patient_service.service.DiseaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiseaseControllerTest {

    @Mock
    private DiseaseService diseaseService;

    @InjectMocks
    private DiseaseController diseaseController;

    @Test
    void createDisease_returns201() {
        DiseaseRequestDto request = new DiseaseRequestDto();
        DiseaseResponseDto response = new DiseaseResponseDto();
        when(diseaseService.createDisease(request)).thenReturn(response);

        ResponseEntity<DiseaseResponseDto> result = diseaseController.createDisease(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getAllDiseases_returns200() {
        List<DiseaseResponseDto> list = List.of(new DiseaseResponseDto());
        when(diseaseService.getAllDiseases()).thenReturn(list);

        ResponseEntity<List<DiseaseResponseDto>> result = diseaseController.getAllDiseases();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void getDiseaseById_returns200() {
        DiseaseResponseDto response = new DiseaseResponseDto();
        when(diseaseService.getDiseaseById(1L)).thenReturn(response);

        ResponseEntity<DiseaseResponseDto> result = diseaseController.getDiseaseById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void updateDisease_returns200() {
        DiseaseRequestDto request = new DiseaseRequestDto();
        DiseaseResponseDto response = new DiseaseResponseDto();
        when(diseaseService.updateDisease(1L, request)).thenReturn(response);

        ResponseEntity<DiseaseResponseDto> result = diseaseController.updateDisease(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void deleteDisease_returns204() {
        doNothing().when(diseaseService).deleteDisease(1L);

        ResponseEntity<Void> result = diseaseController.deleteDisease(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(diseaseService).deleteDisease(1L);
    }
}
