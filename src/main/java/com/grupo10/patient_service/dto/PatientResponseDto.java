package com.grupo10.patient_service.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PatientResponseDto {
    
    private Long idPaciente;
    private Long idUsuario;
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaNacimiento;
    private String telefonoPrincipal;
    private String telefonoSecundario;

}
