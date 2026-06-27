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

/**
 * Servicio de lógica de negocio para el registro de enfermedades crónicas en pacientes
 * y el cálculo de sus umbrales médicos personalizados.
 *
 * <p>Al asociar una enfermedad a un paciente, el servicio determina la regla médica
 * aplicable según la edad del paciente ({@link MedicalRules}) y crea o fusiona los
 * umbrales vitales en {@link MedicalThreshold}. Cuando existen múltiples enfermedades,
 * la fusión adopta los valores más restrictivos: mínimo para los umbrales máximos
 * y máximo para los umbrales mínimos.
 */
@Service
public class ChronicDiseaseService {

    private final PatientRepository patientRepository;
    private final DiseaseRepository diseaseRepository;
    private final PatientDiseaseRepository patientDiseaseRepository;
    private final MedicalThresholdRepository medicalThresholdRepository;

    /**
     * Construye el servicio con los repositorios necesarios inyectados.
     *
     * @param patientRepository          repositorio JPA de pacientes
     * @param diseaseRepository          repositorio JPA de enfermedades
     * @param patientDiseaseRepository   repositorio JPA de relaciones paciente-enfermedad
     * @param medicalThresholdRepository repositorio JPA de umbrales médicos
     */
    public ChronicDiseaseService(PatientRepository patientRepository,
            DiseaseRepository diseaseRepository,
            PatientDiseaseRepository patientDiseaseRepository,
            MedicalThresholdRepository medicalThresholdRepository) {
        this.patientRepository = patientRepository;
        this.diseaseRepository = diseaseRepository;
        this.patientDiseaseRepository = patientDiseaseRepository;
        this.medicalThresholdRepository = medicalThresholdRepository;
    }

    /**
     * Registra una enfermedad crónica a un paciente y actualiza sus umbrales médicos de forma transaccional.
     *
     * <p>Si no existe una regla médica definida para la combinación enfermedad-edad,
     * la operación persiste únicamente la relación paciente-enfermedad sin modificar umbrales.
     *
     * @param idPaciente    identificador del paciente
     * @param idEnfermedad  identificador de la enfermedad crónica
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si el paciente o la enfermedad no existen
     */
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

    /**
     * Recupera los umbrales médicos personalizados de un paciente.
     *
     * @param idPaciente identificador del paciente
     * @return DTO con los umbrales médicos del paciente
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si no hay umbrales registrados para el paciente
     */
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

    /**
     * Calcula la edad en años a partir de la fecha de nacimiento.
     *
     * <p>Devuelve {@code 0} si {@code fechaNacimiento} es nula.
     *
     * @param fechaNacimiento fecha de nacimiento del paciente
     * @return edad en años completos
     */
    private Integer calculateAge(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return 0;
        }
        return Period.between(fechaNacimiento, LocalDate.now(ZoneId.systemDefault())).getYears();
    }
}
