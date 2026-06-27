package com.grupo10.patient_service.repository;

import com.grupo10.patient_service.model.MedicalThreshold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalThresholdRepository extends JpaRepository<MedicalThreshold, Long> {
    Optional<MedicalThreshold> findByPacienteId(Long idPaciente);
}
