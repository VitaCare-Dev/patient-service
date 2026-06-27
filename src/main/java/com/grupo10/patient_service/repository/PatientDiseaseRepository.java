package com.grupo10.patient_service.repository;

import com.grupo10.patient_service.model.PatientDisease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDiseaseRepository extends JpaRepository<PatientDisease, PatientDisease.PatientDiseaseId> {
}
