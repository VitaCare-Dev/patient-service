package com.grupo10.patient_service.service;

import com.grupo10.patient_service.constants.GlobalConstants;
import com.grupo10.patient_service.dto.MedicalThresholdResponseDto;
import com.grupo10.patient_service.exception.ResourceNotFoundException;
import com.grupo10.patient_service.model.Disease;
import com.grupo10.patient_service.model.MedicalRules;
import com.grupo10.patient_service.model.MedicalThreshold;
import com.grupo10.patient_service.model.Patient;
import com.grupo10.patient_service.model.PatientDisease;
import com.grupo10.patient_service.repository.DiseaseRepository;
import com.grupo10.patient_service.repository.MedicalThresholdRepository;
import com.grupo10.patient_service.repository.PatientDiseaseRepository;
import com.grupo10.patient_service.repository.PatientRepository;
import java.time.LocalDate;
import java.time.Period;

import java.time.ZoneId;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChronicDiseaseService {

    private final PatientRepository patientRepository;
    private final DiseaseRepository diseaseRepository;
    private final PatientDiseaseRepository patientDiseaseRepository;
    private final MedicalThresholdRepository medicalThresholdRepository;

    public ChronicDiseaseService(PatientRepository patientRepository,
            DiseaseRepository diseaseRepository,
            PatientDiseaseRepository patientDiseaseRepository,
            MedicalThresholdRepository medicalThresholdRepository) {
        this.patientRepository = patientRepository;
        this.diseaseRepository = diseaseRepository;
        this.patientDiseaseRepository = patientDiseaseRepository;
        this.medicalThresholdRepository = medicalThresholdRepository;
    }

    @Transactional
    public void registerPatientDisease(Long idPaciente, Long idEnfermedad) {
        Patient paciente = patientRepository.findById(idPaciente)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.PATIENT_NOT_FOUND + idPaciente));

        Disease enfermedad = diseaseRepository.findById(idEnfermedad)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.DISEASE_NOT_FOUND + idEnfermedad));

        PatientDisease pacienteEnfermedad = new PatientDisease();
        pacienteEnfermedad.setPaciente(paciente);
        pacienteEnfermedad.setEnfermedad(enfermedad);
        patientDiseaseRepository.save(pacienteEnfermedad);

        Integer edadPaciente = calculateAge(paciente.getFechaNacimiento());
        MedicalRules regla = MedicalRules.findByDiseaseAndAge(enfermedad.getNombreEnfermedad(), edadPaciente);

        if (regla != null) {
            MedicalThreshold umbral = medicalThresholdRepository.findByPacienteId(idPaciente)
                    .orElse(new MedicalThreshold());

            umbral.setPaciente(paciente);

            if (umbral.getIdUmbral() == null) {
                umbral.setGlucosaMax(regla.getGlucosaMax());
                umbral.setGlucosaMin(regla.getGlucosaMin());
                umbral.setSistolicaMax(regla.getSistolicaMax());
                umbral.setDiastolicaMax(regla.getDiastolicaMax());
                umbral.setTemperaturaMax(regla.getTemperaturaMax());
            } else {
                umbral.setGlucosaMax(Math.min(umbral.getGlucosaMax(), regla.getGlucosaMax()));
                umbral.setGlucosaMin(Math.max(umbral.getGlucosaMin(), regla.getGlucosaMin()));
                umbral.setSistolicaMax(Math.min(umbral.getSistolicaMax(), regla.getSistolicaMax()));
                umbral.setDiastolicaMax(Math.min(umbral.getDiastolicaMax(), regla.getDiastolicaMax()));
                umbral.setTemperaturaMax(Math.min(umbral.getTemperaturaMax(), regla.getTemperaturaMax()));
            }

            medicalThresholdRepository.save(umbral);
        }
    }

    public MedicalThresholdResponseDto getPatientThresholds(Long idPaciente) {
        MedicalThreshold umbral = medicalThresholdRepository.findByPacienteId(idPaciente)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Umbral médico no encontrado para el paciente con ID: " + idPaciente));

        MedicalThresholdResponseDto response = new MedicalThresholdResponseDto();
        response.setIdUmbral(umbral.getIdUmbral());
        response.setIdPaciente(umbral.getPaciente().getId());
        response.setGlucosaMax(umbral.getGlucosaMax());
        response.setGlucosaMin(umbral.getGlucosaMin());
        response.setSistolicaMax(umbral.getSistolicaMax());
        response.setDiastolicaMax(umbral.getDiastolicaMax());
        response.setTemperaturaMax(umbral.getTemperaturaMax());

        return response;
    }

    private Integer calculateAge(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return 0;
        }
        return Period.between(fechaNacimiento, LocalDate.now(ZoneId.systemDefault())).getYears();
    }
}
