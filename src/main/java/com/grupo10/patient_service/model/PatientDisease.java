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

@Data
@Entity
@Table(name = "tb_paciente_enfermedad")
@IdClass(PatientDisease.PatientDiseaseId.class)
public class PatientDisease {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente")
    private Patient paciente;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_enfermedad")
    private Disease enfermedad;

    @Data
    public static class PatientDiseaseId implements Serializable {
        private Long paciente;
        private Long enfermedad;
    }
}
