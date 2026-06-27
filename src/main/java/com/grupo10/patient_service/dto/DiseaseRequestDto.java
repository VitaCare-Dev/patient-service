package com.grupo10.patient_service.dto;

import lombok.Data;

/**
 * DTO de entrada para la creación o actualización de una enfermedad crónica en el catálogo.
 */
@Data
public class DiseaseRequestDto {

    private String nombreEnfermedad;
    private String descripcion;
}
