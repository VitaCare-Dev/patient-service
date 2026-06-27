package com.grupo10.patient_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad JPA que representa una enfermedad crónica definida en el catálogo del sistema.
 *
 * <p>Las enfermedades disponibles son referenciadas por las reglas médicas en
 * {@link MedicalRules} para calcular los umbrales vitales por paciente.
 */
@Data
@Entity
@Table(name = "tb_enfermedad")
public class Disease {

    /** Identificador único autogenerado de la enfermedad. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_enfermedad")
    private Long idEnfermedad;

    /** Nombre de la enfermedad crónica. */
    @Column(name = "nombre_enfermedad")
    private String nombreEnfermedad;

    /** Descripción detallada de la enfermedad. Almacenada como objeto grande (LOB). */
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
}
