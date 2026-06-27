package com.grupo10.patient_service.repository;

import com.grupo10.patient_service.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
