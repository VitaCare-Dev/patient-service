package com.grupo10.patient_service.controller;

import com.grupo10.patient_service.dto.DiseaseResponseDto;
import com.grupo10.patient_service.dto.MedicalThresholdResponseDto;
import com.grupo10.patient_service.dto.PatientDiseaseRequestDto;
import com.grupo10.patient_service.service.ChronicDiseaseService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChronicDiseaseControllerTest {

    @Mock
    private ChronicDiseaseService chronicDiseaseService;

    @InjectMocks
    private ChronicDiseaseController chronicDiseaseController;

    @Test
    void registerPatientDisease_returns201() {
        PatientDiseaseRequestDto request = new PatientDiseaseRequestDto();
        request.setIdPaciente(1L);
        request.setIdEnfermedad(2L);
        doNothing().when(chronicDiseaseService).registerPatientDisease(1L, 2L);

        ResponseEntity<Void> result = chronicDiseaseController.registerPatientDisease(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(chronicDiseaseService).registerPatientDisease(1L, 2L);
    }

    @Test
    void getPatientThresholds_returns200() {
        MedicalThresholdResponseDto response = new MedicalThresholdResponseDto();
        when(chronicDiseaseService.getPatientThresholds(1L)).thenReturn(response);

        ResponseEntity<MedicalThresholdResponseDto> result = chronicDiseaseController.getPatientThresholds(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getPatientDiseases_returns200() {
        List<DiseaseResponseDto> response = List.of(new DiseaseResponseDto());
        when(chronicDiseaseService.getPatientDiseases(1L)).thenReturn(response);

        ResponseEntity<List<DiseaseResponseDto>> result = chronicDiseaseController.getPatientDiseases(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}
