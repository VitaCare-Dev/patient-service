package com.grupo10.patient_service.controller;

import com.grupo10.patient_service.dto.PatientRequestDto;
import com.grupo10.patient_service.dto.PatientResponseDto;
import com.grupo10.patient_service.service.PatientService;
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
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Test
    void createPatient_returns201() {
        PatientRequestDto request = new PatientRequestDto();
        PatientResponseDto response = new PatientResponseDto();
        when(patientService.createPatient(request)).thenReturn(response);

        ResponseEntity<PatientResponseDto> result = patientController.createPatient(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getAllPatients_returns200() {
        List<PatientResponseDto> list = List.of(new PatientResponseDto());
        when(patientService.getAllPatients()).thenReturn(list);

        ResponseEntity<List<PatientResponseDto>> result = patientController.getAllPatients();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void getPatientById_returns200() {
        PatientResponseDto response = new PatientResponseDto();
        when(patientService.getPatientById(1L)).thenReturn(response);

        ResponseEntity<PatientResponseDto> result = patientController.getPatientById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getPatientByIdUsuario_returns200() {
        PatientResponseDto response = new PatientResponseDto();
        when(patientService.getPatientByIdUsuario(5L)).thenReturn(response);

        ResponseEntity<PatientResponseDto> result = patientController.getPatientByIdUsuario(5L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void updatePatient_returns200() {
        PatientRequestDto request = new PatientRequestDto();
        PatientResponseDto response = new PatientResponseDto();
        when(patientService.updatePatient(1L, request)).thenReturn(response);

        ResponseEntity<PatientResponseDto> result = patientController.updatePatient(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void deletePatient_returns204() {
        doNothing().when(patientService).deletePatient(1L);

        ResponseEntity<Void> result = patientController.deletePatient(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(patientService).deletePatient(1L);
    }
}
