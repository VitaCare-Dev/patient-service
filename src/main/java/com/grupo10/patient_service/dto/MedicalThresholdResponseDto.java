package com.grupo10.patient_service.dto;

import lombok.Data;

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
