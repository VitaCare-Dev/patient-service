package com.grupo10.patient_service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientResponseDto {
    
    private Long idPaciente;
    private Long idUsuario;
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String telefonoPrincipal;
    private String telefonoSecundario;
    
}
