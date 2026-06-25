package com.grupo10.patient_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_enfermedad")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_enfermedad")
    private Long idEnfermedad;

    @Column(name = "nombre_enfermedad")
    private String nombreEnfermedad;

    @Column(name = "descripcion")
    private String descripcion;
}
