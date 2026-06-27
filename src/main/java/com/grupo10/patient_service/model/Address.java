package com.grupo10.patient_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;


/**
 * Entidad JPA que representa la dirección de un paciente.
 *
 * <p>Un paciente puede tener múltiples direcciones registradas.
 * La relación con {@link Patient} se carga de forma diferida (lazy).
 */
@Data
@Entity
@Table(name = "tb_direccion")
public class Address{

    /** Identificador único autogenerado de la dirección. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIRECCION")
    private Long idDireccion;

    /** Paciente propietario de esta dirección. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACIENTE")
    private Patient paciente;

    /** Nombre de la calle. */
    @Column(name = "CALLE")
    private String calle;

    /** Número de la propiedad. */
    @Column(name = "NUMERO")
    private String numero;

    /** Comuna donde se ubica la dirección. */
    @Column(name = "COMUNA")
    private String comuna;

    /** Región donde se ubica la dirección. */
    @Column(name = "REGION")
    private String region;
}

