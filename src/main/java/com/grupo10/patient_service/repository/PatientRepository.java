package com.grupo10.patient_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grupo10.patient_service.model.Patient;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByIdUsuario(Long idUsuario);

    Optional<Patient> findByRut(String rut);

    boolean existsByRut(String rut);
}
