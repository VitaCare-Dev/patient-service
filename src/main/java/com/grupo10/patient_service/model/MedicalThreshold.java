package com.grupo10.patient_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad JPA que almacena los umbrales médicos personalizados de un paciente.
 *
 * <p>Los valores se calculan a partir de las enfermedades crónicas registradas y la edad
 * del paciente, aplicando la lógica definida en {@link MedicalRules}. Cuando un paciente
 * tiene más de una enfermedad, los umbrales se fusionan adoptando los valores más restrictivos.
 */
@Data
@Entity
@Table(name = "tb_umbral_medico")
public class MedicalThreshold {

    /** Identificador único autogenerado del umbral médico. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_umbral")
    private Long idUmbral;

    /** Paciente al que pertenecen estos umbrales. Relación uno-a-uno única. */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", unique = true)
    private Patient paciente;

    /** Nivel máximo de glucosa en sangre permitido (mg/dL). */
    @Column(name = "glucosa_max")
    private Integer glucosaMax;

    /** Nivel mínimo de glucosa en sangre permitido (mg/dL). */
    @Column(name = "glucosa_min")
    private Integer glucosaMin;

    /** Presión arterial sistólica máxima permitida (mmHg). */
    @Column(name = "sistolica_max")
    private Integer sistolicaMax;

    /** Presión arterial diastólica máxima permitida (mmHg). */
    @Column(name = "diastolica_max")
    private Integer diastolicaMax;

    /** Temperatura corporal máxima permitida (°C). */
    @Column(name = "temperatura_max")
    private Double temperaturaMax;
}
