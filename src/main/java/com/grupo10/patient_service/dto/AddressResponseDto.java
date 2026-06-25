package com.grupo10.patient_service.dto;

import lombok.Data;

@Data
public class AddressResponseDto {

    private Long idDireccion;
    private Long idPaciente;
    private String calle;
    private String numero;
    private String comuna;
    private String region;
}
