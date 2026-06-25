package com.grupo10.patient_service.service;

import com.grupo10.patient_service.constants.GlobalConstants;
import com.grupo10.patient_service.dto.AddressRequestDto;
import com.grupo10.patient_service.dto.AddressResponseDto;
import com.grupo10.patient_service.exception.ResourceNotFoundException;
import com.grupo10.patient_service.model.Address;
import com.grupo10.patient_service.model.Patient;
import com.grupo10.patient_service.repository.AddressRepository;
import com.grupo10.patient_service.repository.PatientRepository;
import com.grupo10.patient_service.util.UpdateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final PatientRepository patientRepository;

    public AddressService(AddressRepository addressRepository, PatientRepository patientRepository) {
        this.addressRepository = addressRepository;
        this.patientRepository = patientRepository;
    }

    public AddressResponseDto createAddress(AddressRequestDto request) {
        Patient paciente = patientRepository.findById(request.getIdPaciente())
                .orElseThrow(() -> new ResourceNotFoundException(
                        GlobalConstants.PATIENT_NOT_FOUND + request.getIdPaciente()));

        Address direccion = new Address();
        direccion.setPaciente(paciente);
        direccion.setCalle(request.getCalle());
        direccion.setNumero(request.getNumero());
        direccion.setComuna(request.getComuna());
        direccion.setRegion(request.getRegion());

        Address direccionGuardada = addressRepository.save(direccion);
        return mapToResponse(direccionGuardada);
    }

    public AddressResponseDto getAddressById(Long id) {
        Address direccion = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.ADDRESS_NOT_FOUND + id));
        return mapToResponse(direccion);
    }

    public List<AddressResponseDto> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AddressResponseDto> getAddressesByPatientId(Long idPaciente) {
        if (!patientRepository.existsById(idPaciente)) {
            throw new ResourceNotFoundException(GlobalConstants.PATIENT_NOT_FOUND + idPaciente);
        }
        return addressRepository.findByPacienteId(idPaciente).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AddressResponseDto updateAddress(Long id, AddressRequestDto request) {
        Address direccion = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.ADDRESS_NOT_FOUND + id));

        if (request.getIdPaciente() != null && !request.getIdPaciente().equals(direccion.getPaciente().getId())) {
            Patient nuevoPaciente = patientRepository.findById(request.getIdPaciente())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            GlobalConstants.PATIENT_NOT_FOUND + request.getIdPaciente()));
            direccion.setPaciente(nuevoPaciente);
        }

        UpdateUtil.copyNonNullProperties(request, direccion);

        Address direccionActualizada = addressRepository.save(direccion);
        return mapToResponse(direccionActualizada);
    }

    public void deleteAddress(Long id) {
        Address direccion = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.ADDRESS_NOT_FOUND + id));
        addressRepository.delete(direccion);
    }

    private AddressResponseDto mapToResponse(Address direccion) {
        AddressResponseDto response = new AddressResponseDto();
        response.setIdDireccion(direccion.getIdDireccion());
        response.setIdPaciente(direccion.getPaciente().getId());
        response.setCalle(direccion.getCalle());
        response.setNumero(direccion.getNumero());
        response.setComuna(direccion.getComuna());
        response.setRegion(direccion.getRegion());
        return response;
    }
}
