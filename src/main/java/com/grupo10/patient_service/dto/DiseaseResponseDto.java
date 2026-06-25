package com.grupo10.patient_service.dto;

import lombok.Data;

@Data
public class DiseaseResponseDto {

    private Long idEnfermedad;
    private String nombreEnfermedad;
    private String descripcion;
}
