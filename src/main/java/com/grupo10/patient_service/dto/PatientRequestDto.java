package com.grupo10.patient_service.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * DTO de entrada para la creación o actualización de un paciente.
 *
 * <p>Los campos {@code idUsuario} y {@code rut} son inmutables una vez registrados;
 * {@link com.grupo10.patient_service.util.UpdateUtil} los excluye automáticamente
 * en operaciones de actualización parcial.
 */
@Data
public class PatientRequestDto {

    private Long idUsuario; 
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String telefonoPrincipal;
    private String telefonoSecundario;
}
