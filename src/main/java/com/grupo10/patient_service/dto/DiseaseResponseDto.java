package com.grupo10.patient_service.dto;

import lombok.Data;

/**
 * DTO de salida con los datos de una enfermedad crónica.
 *
 * <p>Incluye el {@code idEnfermedad} generado por el sistema junto al nombre y descripción.
 */
@Data
public class DiseaseResponseDto {

    private Long idEnfermedad;
    private String nombreEnfermedad;
    private String descripcion;
}
