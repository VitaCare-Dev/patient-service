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

@Data
@Entity
@Table(name = "tb_umbral_medico")
public class MedicalThreshold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_umbral")
    private Long idUmbral;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", unique = true)
    private Patient paciente;

    @Column(name = "glucosa_max")
    private Integer glucosaMax;

    @Column(name = "glucosa_min")
    private Integer glucosaMin;

    @Column(name = "sistolica_max")
    private Integer sistolicaMax;

    @Column(name = "diastolica_max")
    private Integer diastolicaMax;

    @Column(name = "temperatura_max")
    private Double temperaturaMax;
}
