package com.grupo10.patient_service.repository;

import com.grupo10.patient_service.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByPacienteId(Long idPaciente);
}
