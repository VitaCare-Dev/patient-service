package com.grupo10.patient_service.service;

import com.grupo10.patient_service.dto.PatientRequestDto;
import com.grupo10.patient_service.dto.PatientResponseDto;
import com.grupo10.patient_service.exception.DuplicateResourceException;
import com.grupo10.patient_service.exception.ResourceNotFoundException;
import com.grupo10.patient_service.exception.UserNotFoundException;
import com.grupo10.patient_service.model.Patient;
import com.grupo10.patient_service.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient buildPatient() {
        Patient p = new Patient();
        p.setId(1L);
        p.setIdUsuario(10L);
        p.setRut("12345678-9");
        p.setNombre("Juan");
        p.setApellidoPaterno("Pérez");
        p.setApellidoMaterno("González");
        p.setFechaNacimiento(LocalDate.of(1985, 3, 15));
        p.setTelefonoPrincipal("+56912345678");
        p.setTelefonoSecundario("+56987654321");
        return p;
    }

    private PatientRequestDto buildRequest() {
        PatientRequestDto dto = new PatientRequestDto();
        dto.setIdUsuario(10L);
        dto.setRut("12345678-9");
        dto.setNombre("Juan");
        dto.setApellidoPaterno("Pérez");
        dto.setApellidoMaterno("González");
        dto.setFechaNacimiento(LocalDate.of(1985, 3, 15));
        dto.setTelefonoPrincipal("+56912345678");
        dto.setTelefonoSecundario("+56987654321");
        return dto;
    }

    @Test
    void createPatient_success() {
        PatientRequestDto request = buildRequest();
        Patient saved = buildPatient();

        when(patientRepository.existsByIdUsuario(10L)).thenReturn(false);
        when(patientRepository.existsByRut("12345678-9")).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenReturn(saved);

        PatientResponseDto result = patientService.createPatient(request);

        assertNotNull(result);
        assertEquals(1L, result.getIdPaciente());
        assertEquals(10L, result.getIdUsuario());
        assertEquals("12345678-9", result.getRut());
        assertEquals("Juan", result.getNombre());
        assertEquals("Pérez", result.getApellidoPaterno());
        assertEquals("González", result.getApellidoMaterno());
        assertEquals(LocalDate.of(1985, 3, 15), result.getFechaNacimiento());
        assertEquals("+56912345678", result.getTelefonoPrincipal());
        assertEquals("+56987654321", result.getTelefonoSecundario());
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void createPatient_nullUserId_throwsUserNotFoundException() {
        PatientRequestDto request = buildRequest();
        request.setIdUsuario(null);

        assertThrows(UserNotFoundException.class, () -> patientService.createPatient(request));
        verifyNoInteractions(patientRepository);
    }

    @Test
    void createPatient_duplicateUser_throwsDuplicateResourceException() {
        PatientRequestDto request = buildRequest();
        when(patientRepository.existsByIdUsuario(10L)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> patientService.createPatient(request));
        verify(patientRepository, never()).save(any());
    }

    @Test
    void createPatient_duplicateRut_throwsDuplicateResourceException() {
        PatientRequestDto request = buildRequest();
        when(patientRepository.existsByIdUsuario(10L)).thenReturn(false);
        when(patientRepository.existsByRut("12345678-9")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> patientService.createPatient(request));
        verify(patientRepository, never()).save(any());
    }

    @Test
    void getPatientById_found() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(buildPatient()));

        PatientResponseDto result = patientService.getPatientById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdPaciente());
    }

    @Test
    void getPatientById_notFound_throwsResourceNotFoundException() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientById(99L));
    }

    @Test
    void getPatientByIdUsuario_found() {
        when(patientRepository.findByIdUsuario(10L)).thenReturn(Optional.of(buildPatient()));

        PatientResponseDto result = patientService.getPatientByIdUsuario(10L);

        assertNotNull(result);
        assertEquals(1L, result.getIdPaciente());
    }

    @Test
    void getPatientByIdUsuario_notFound_throwsResourceNotFoundException() {
        when(patientRepository.findByIdUsuario(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientByIdUsuario(99L));
    }

    @Test
    void getAllPatients_returnsList() {
        when(patientRepository.findAll()).thenReturn(List.of(buildPatient(), buildPatient()));

        List<PatientResponseDto> result = patientService.getAllPatients();

        assertEquals(2, result.size());
    }

    @Test
    void getAllPatients_emptyList() {
        when(patientRepository.findAll()).thenReturn(List.of());

        List<PatientResponseDto> result = patientService.getAllPatients();

        assertTrue(result.isEmpty());
    }

    @Test
    void updatePatient_success() {
        Patient existing = buildPatient();
        PatientRequestDto request = buildRequest();
        request.setNombre("Carlos");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(patientRepository.save(any(Patient.class))).thenReturn(existing);

        PatientResponseDto result = patientService.updatePatient(1L, request);

        assertNotNull(result);
        verify(patientRepository).save(existing);
    }

    @Test
    void updatePatient_notFound_throwsResourceNotFoundException() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.updatePatient(99L, buildRequest()));
    }

    @Test
    void deletePatient_success() {
        Patient patient = buildPatient();
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        patientService.deletePatient(1L);

        verify(patientRepository).delete(patient);
    }

    @Test
    void deletePatient_notFound_throwsResourceNotFoundException() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.deletePatient(99L));
    }
}
