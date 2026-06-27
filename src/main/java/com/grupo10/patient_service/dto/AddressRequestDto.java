package com.grupo10.patient_service.dto;

import lombok.Data;

/**
 * DTO de entrada para la creación o actualización de una dirección.
 *
 * <p>El campo {@code idPaciente} referencia al paciente propietario de la dirección.
 * En actualizaciones, si se proporciona un {@code idPaciente} diferente al actual,
 * la dirección será reasignada al nuevo paciente.
 */
@Data
public class AddressRequestDto {

    private Long idPaciente;
    private String calle;
    private String numero;
    private String comuna;
    private String region;
}
