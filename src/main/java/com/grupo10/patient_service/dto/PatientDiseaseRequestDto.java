package com.grupo10.patient_service.dto;

import lombok.Data;

/**
 * DTO de entrada para registrar una enfermedad crónica a un paciente.
 *
 * <p>Al procesar esta solicitud, el sistema crea la asociación paciente-enfermedad
 * y recalcula o inicializa los umbrales médicos del paciente según las reglas definidas
 * en {@link com.grupo10.patient_service.model.MedicalRules}.
 */
@Data
public class PatientDiseaseRequestDto {

    private Long idPaciente;
    private Long idEnfermedad;
}
