package com.grupo10.patient_service.dto;

import lombok.Data;

/**
 * DTO de salida con los datos de una dirección registrada.
 *
 * <p>Incluye el {@code idDireccion} generado por el sistema y el {@code idPaciente}
 * al que pertenece la dirección.
 */
@Data
public class AddressResponseDto {

    private Long idDireccion;
    private Long idPaciente;
    private String calle;
    private String numero;
    private String comuna;
    private String region;
}
