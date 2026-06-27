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

/**
 * Servicio de lógica de negocio para la gestión de direcciones de pacientes.
 *
 * <p>Valida la existencia del paciente antes de crear o reasignar una dirección,
 * y soporta actualizaciones parciales mediante {@link UpdateUtil}.
 */
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final PatientRepository patientRepository;

    /**
     * Construye el servicio con los repositorios de direcciones y pacientes inyectados.
     *
     * @param addressRepository repositorio JPA de direcciones
     * @param patientRepository repositorio JPA de pacientes
     */
    public AddressService(AddressRepository addressRepository, PatientRepository patientRepository) {
        this.addressRepository = addressRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Crea y persiste una nueva dirección para el paciente indicado.
     *
     * @param request datos de la dirección a registrar
     * @return DTO con los datos de la dirección creada
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si el paciente no existe
     */
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

    /**
     * Recupera una dirección por su identificador.
     *
     * @param id identificador de la dirección
     * @return DTO con los datos de la dirección
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si la dirección no existe
     */
    public AddressResponseDto getAddressById(Long id) {
        Address direccion = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GlobalConstants.ADDRESS_NOT_FOUND + id));
        return mapToResponse(direccion);
    }

    /**
     * Recupera todas las direcciones registradas en el sistema.
     *
     * @return lista de DTOs de direcciones; vacía si no hay registros
     */
    public List<AddressResponseDto> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Recupera todas las direcciones asociadas a un paciente específico.
     *
     * @param idPaciente identificador del paciente
     * @return lista de DTOs de direcciones del paciente
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si el paciente no existe
     */
    public List<AddressResponseDto> getAddressesByPatientId(Long idPaciente) {
        if (!patientRepository.existsById(idPaciente)) {
            throw new ResourceNotFoundException(GlobalConstants.PATIENT_NOT_FOUND + idPaciente);
        }
        return addressRepository.findByPacienteId(idPaciente).stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Actualiza parcialmente los datos de una dirección existente.
     *
     * <p>Si {@code request} incluye un {@code idPaciente} diferente al actual,
     * la dirección es reasignada al nuevo paciente (que debe existir).
     *
     * @param id      identificador de la dirección a actualizar
     * @param request campos con los nuevos valores
     * @return DTO con los datos actualizados de la dirección
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si la dirección o el nuevo paciente no existen
     */
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

    /**
     * Elimina una dirección del sistema por su identificador.
     *
     * @param id identificador de la dirección a eliminar
     * @throws com.grupo10.patient_service.exception.ResourceNotFoundException si la dirección no existe
     */
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
