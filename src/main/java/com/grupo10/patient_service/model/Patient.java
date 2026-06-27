package com.grupo10.patient_service.model;



import java.time.LocalDate;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Entidad JPA que representa a un paciente en el sistema de salud.
 *
 * <p>Cada paciente está vinculado a un usuario del sistema mediante {@code idUsuario}
 * y se identifica de forma única por su RUT.
 */
@Data
@Entity
@Table(name = "tb_paciente")
public class Patient {

    /** Identificador único autogenerado del paciente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long id;

    /** Identificador del usuario asociado en el sistema de autenticación. */
    @Column(name = "id_usuario")
    private Long idUsuario;

    /** RUT del paciente. Debe ser único en el sistema. */
    @Column(name = "rut")
    private String rut;

    /** Nombre de pila del paciente. */
    @Column(name = "nombre")
    private String nombre;

    /** Apellido paterno del paciente. */
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    /** Apellido materno del paciente. */
    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    /** Fecha de nacimiento del paciente. */
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    /** Número de teléfono principal de contacto. */
    @Column(name = "telefono_principal")
    private String telefonoPrincipal;

    /** Número de teléfono secundario de contacto. */
    @Column(name = "telefono_secundario")
    private String telefonoSecundario;

}
