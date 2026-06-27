package com.grupo10.patient_service.service;

import com.grupo10.patient_service.dto.MedicalThresholdResponseDto;
import com.grupo10.patient_service.exception.ResourceNotFoundException;
import com.grupo10.patient_service.model.Disease;
import com.grupo10.patient_service.model.MedicalThreshold;
import com.grupo10.patient_service.model.Patient;
import com.grupo10.patient_service.repository.DiseaseRepository;
import com.grupo10.patient_service.repository.MedicalThresholdRepository;
import com.grupo10.patient_service.repository.PatientDiseaseRepository;
import com.grupo10.patient_service.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChronicDiseaseServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DiseaseRepository diseaseRepository;

    @Mock
    private PatientDiseaseRepository patientDiseaseRepository;

    @Mock
    private MedicalThresholdRepository medicalThresholdRepository;

    @InjectMocks
    private ChronicDiseaseService chronicDiseaseService;

    private Patient buildPatient(LocalDate fechaNacimiento) {
        Patient p = new Patient();
        p.setId(1L);
        p.setNombre("Juan");
        p.setFechaNacimiento(fechaNacimiento);
        return p;
    }

    private Disease buildDisease(String nombre) {
        Disease d = new Disease();
        d.setIdEnfermedad(1L);
        d.setNombreEnfermedad(nombre);
        return d;
    }

    private MedicalThreshold buildExistingThreshold(Patient patient) {
        MedicalThreshold t = new MedicalThreshold();
        t.setIdUmbral(100L);
        t.setPaciente(patient);
        t.setGlucosaMax(200);
        t.setGlucosaMin(60);
        t.setSistolicaMax(150);
        t.setDiastolicaMax(100);
        t.setTemperaturaMax(38.0);
        return t;
    }

    // --- registerPatientDisease ---

    @Test
    void registerPatientDisease_patientNotFound_throwsResourceNotFoundException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> chronicDiseaseService.registerPatientDisease(1L, 1L));
        verifyNoInteractions(diseaseRepository, patientDiseaseRepository, medicalThresholdRepository);
    }

    @Test
    void registerPatientDisease_diseaseNotFound_throwsResourceNotFoundException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(buildPatient(LocalDate.of(1985, 1, 1))));
        when(diseaseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> chronicDiseaseService.registerPatientDisease(1L, 1L));
        verifyNoInteractions(patientDiseaseRepository, medicalThresholdRepository);
    }

    @Test
    void registerPatientDisease_withMatchingRule_createsNewThreshold() {
        Patient patient = buildPatient(LocalDate.of(1985, 1, 1)); // ~41 años → adulto
        Disease disease = buildDisease("Diabetes");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(diseaseRepository.findById(1L)).thenReturn(Optional.of(disease));
        when(patientDiseaseRepository.save(any())).thenReturn(null);
        when(medicalThresholdRepository.findByPacienteId(1L)).thenReturn(Optional.empty());
        when(medicalThresholdRepository.save(any(MedicalThreshold.class))).thenReturn(new MedicalThreshold());

        assertDoesNotThrow(() -> chronicDiseaseService.registerPatientDisease(1L, 1L));

        verify(medicalThresholdRepository).save(any(MedicalThreshold.class));
    }

    @Test
    void registerPatientDisease_withMatchingRule_mergesExistingThreshold() {
        Patient patient = buildPatient(LocalDate.of(1985, 1, 1)); // ~41 años → adulto
        Disease disease = buildDisease("Diabetes");
        MedicalThreshold existing = buildExistingThreshold(patient);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(diseaseRepository.findById(1L)).thenReturn(Optional.of(disease));
        when(patientDiseaseRepository.save(any())).thenReturn(null);
        when(medicalThresholdRepository.findByPacienteId(1L)).thenReturn(Optional.of(existing));
        when(medicalThresholdRepository.save(any(MedicalThreshold.class))).thenReturn(existing);

        assertDoesNotThrow(() -> chronicDiseaseService.registerPatientDisease(1L, 1L));

        // DIABETES_ADULTO: glucosaMax=180, glucosaMin=70, sistolicaMax=140, diastolicaMax=90, tempMax=37.5
        // existing: glucosaMax=200, glucosaMin=60, sistolicaMax=150, diastolicaMax=100, tempMax=38.0
        // merge: min(200,180)=180, max(60,70)=70, min(150,140)=140, min(100,90)=90, min(38.0,37.5)=37.5
        assertEquals(180, existing.getGlucosaMax());
        assertEquals(70, existing.getGlucosaMin());
        assertEquals(140, existing.getSistolicaMax());
        assertEquals(90, existing.getDiastolicaMax());
        assertEquals(37.5, existing.getTemperaturaMax());
        verify(medicalThresholdRepository).save(existing);
    }

    @Test
    void registerPatientDisease_noMatchingRule_doesNotCreateThreshold() {
        Patient patient = buildPatient(LocalDate.of(1985, 1, 1));
        Disease disease = buildDisease("EnfermedadDesconocida");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(diseaseRepository.findById(1L)).thenReturn(Optional.of(disease));
        when(patientDiseaseRepository.save(any())).thenReturn(null);

        assertDoesNotThrow(() -> chronicDiseaseService.registerPatientDisease(1L, 1L));

        verify(medicalThresholdRepository, never()).save(any());
    }

    @Test
    void registerPatientDisease_nullFechaNacimiento_noMatchingRule() {
        Patient patient = buildPatient(null); // edad = 0 → sin regla
        Disease disease = buildDisease("Diabetes");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(diseaseRepository.findById(1L)).thenReturn(Optional.of(disease));
        when(patientDiseaseRepository.save(any())).thenReturn(null);

        assertDoesNotThrow(() -> chronicDiseaseService.registerPatientDisease(1L, 1L));

        verify(medicalThresholdRepository, never()).save(any());
    }

    // --- getPatientThresholds ---

    @Test
    void getPatientThresholds_found() {
        Patient patient = buildPatient(LocalDate.of(1980, 1, 1));
        MedicalThreshold threshold = buildExistingThreshold(patient);

        when(medicalThresholdRepository.findByPacienteId(1L)).thenReturn(Optional.of(threshold));

        MedicalThresholdResponseDto result = chronicDiseaseService.getPatientThresholds(1L);

        assertNotNull(result);
        assertEquals(100L, result.getIdUmbral());
        assertEquals(1L, result.getIdPaciente());
        assertEquals(200, result.getGlucosaMax());
        assertEquals(60, result.getGlucosaMin());
        assertEquals(150, result.getSistolicaMax());
        assertEquals(100, result.getDiastolicaMax());
        assertEquals(38.0, result.getTemperaturaMax());
    }

    @Test
    void getPatientThresholds_notFound_throwsResourceNotFoundException() {
        when(medicalThresholdRepository.findByPacienteId(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> chronicDiseaseService.getPatientThresholds(99L));
    }
}
