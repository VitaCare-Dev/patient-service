package com.grupo10.patient_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * Entidad JPA que representa la relación muchos-a-muchos entre pacientes y enfermedades crónicas.
 *
 * <p>Utiliza una clave primaria compuesta ({@link PatientDiseaseId}) formada por
 * el identificador del paciente y el de la enfermedad.
 */
@Data
@Entity
@Table(name = "tb_paciente_enfermedad")
@IdClass(PatientDisease.PatientDiseaseId.class)
public class PatientDisease {

    /** Paciente asociado a la relación. */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente")
    private Patient paciente;

    /** Enfermedad crónica asociada al paciente. */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_enfermedad")
    private Disease enfermedad;

    /**
     * Clase de clave primaria compuesta para {@link PatientDisease}.
     *
     * <p>Combina los identificadores del paciente y la enfermedad para
     * garantizar unicidad en la tabla de asociación.
     */
    @Data
    public static class PatientDiseaseId implements Serializable {
        /** Identificador del paciente. */
        private Long paciente;
        /** Identificador de la enfermedad. */
        private Long enfermedad;
    }
}
