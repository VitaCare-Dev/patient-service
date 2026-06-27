package com.grupo10.patient_service.dto;

import lombok.Data;

/**
 * DTO de salida con los umbrales médicos personalizados de un paciente.
 *
 * <p>Refleja los valores calculados en {@link com.grupo10.patient_service.model.MedicalThreshold}
 * después de aplicar y fusionar las reglas de todas las enfermedades crónicas del paciente.
 */
@Data
public class MedicalThresholdResponseDto {

    private Long idUmbral;
    private Long idPaciente;
    private Integer glucosaMax;
    private Integer glucosaMin;
    private Integer sistolicaMax;
    private Integer diastolicaMax;
    private Double temperaturaMax;
}
