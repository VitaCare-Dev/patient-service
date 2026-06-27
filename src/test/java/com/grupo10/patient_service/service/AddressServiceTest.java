package com.grupo10.patient_service.service;

import com.grupo10.patient_service.dto.AddressRequestDto;
import com.grupo10.patient_service.dto.AddressResponseDto;
import com.grupo10.patient_service.exception.ResourceNotFoundException;
import com.grupo10.patient_service.model.Address;
import com.grupo10.patient_service.model.Patient;
import com.grupo10.patient_service.repository.AddressRepository;
import com.grupo10.patient_service.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AddressService addressService;

    private Patient buildPatient(Long id) {
        Patient p = new Patient();
        p.setId(id);
        p.setNombre("Juan");
        return p;
    }

    private Address buildAddress(Long id, Patient patient) {
        Address a = new Address();
        a.setIdDireccion(id);
        a.setPaciente(patient);
        a.setCalle("Av. Providencia");
        a.setNumero("1234");
        a.setComuna("Providencia");
        a.setRegion("Metropolitana");
        return a;
    }

    private AddressRequestDto buildRequest(Long idPaciente) {
        AddressRequestDto dto = new AddressRequestDto();
        dto.setIdPaciente(idPaciente);
        dto.setCalle("Av. Providencia");
        dto.setNumero("1234");
        dto.setComuna("Providencia");
        dto.setRegion("Metropolitana");
        return dto;
    }

    @Test
    void createAddress_success() {
        Patient patient = buildPatient(1L);
        Address saved = buildAddress(10L, patient);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(addressRepository.save(any(Address.class))).thenReturn(saved);

        AddressResponseDto result = addressService.createAddress(buildRequest(1L));

        assertNotNull(result);
        assertEquals(10L, result.getIdDireccion());
        assertEquals(1L, result.getIdPaciente());
        assertEquals("Av. Providencia", result.getCalle());
        assertEquals("1234", result.getNumero());
        assertEquals("Providencia", result.getComuna());
        assertEquals("Metropolitana", result.getRegion());
    }

    @Test
    void createAddress_patientNotFound_throwsResourceNotFoundException() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.createAddress(buildRequest(99L)));
        verify(addressRepository, never()).save(any());
    }

    @Test
    void getAddressById_found() {
        Patient patient = buildPatient(1L);
        Address address = buildAddress(10L, patient);
        when(addressRepository.findById(10L)).thenReturn(Optional.of(address));

        AddressResponseDto result = addressService.getAddressById(10L);

        assertNotNull(result);
        assertEquals(10L, result.getIdDireccion());
    }

    @Test
    void getAddressById_notFound_throwsResourceNotFoundException() {
        when(addressRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressById(99L));
    }

    @Test
    void getAllAddresses_returnsList() {
        Patient patient = buildPatient(1L);
        when(addressRepository.findAll()).thenReturn(List.of(buildAddress(1L, patient), buildAddress(2L, patient)));

        List<AddressResponseDto> result = addressService.getAllAddresses();

        assertEquals(2, result.size());
    }

    @Test
    void getAddressesByPatientId_success() {
        Patient patient = buildPatient(1L);
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(addressRepository.findByPacienteId(1L)).thenReturn(List.of(buildAddress(10L, patient)));

        List<AddressResponseDto> result = addressService.getAddressesByPatientId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getAddressesByPatientId_patientNotFound_throwsResourceNotFoundException() {
        when(patientRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressesByPatientId(99L));
        verify(addressRepository, never()).findByPacienteId(any());
    }

    @Test
    void updateAddress_nullPatientId_keepsSamePatient() {
        Patient patient = buildPatient(1L);
        Address existing = buildAddress(10L, patient);
        AddressRequestDto request = buildRequest(null);

        when(addressRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(addressRepository.save(any(Address.class))).thenReturn(existing);

        AddressResponseDto result = addressService.updateAddress(10L, request);

        assertNotNull(result);
        verify(patientRepository, never()).findById(any());
    }

    @Test
    void updateAddress_samePatientId_keepsSamePatient() {
        Patient patient = buildPatient(1L);
        Address existing = buildAddress(10L, patient);
        AddressRequestDto request = buildRequest(1L);

        when(addressRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(addressRepository.save(any(Address.class))).thenReturn(existing);

        AddressResponseDto result = addressService.updateAddress(10L, request);

        assertNotNull(result);
        verify(patientRepository, never()).findById(any());
    }

    @Test
    void updateAddress_differentPatientId_updatesPatient() {
        Patient currentPatient = buildPatient(1L);
        Patient newPatient = buildPatient(2L);
        Address existing = buildAddress(10L, currentPatient);
        AddressRequestDto request = buildRequest(2L);

        when(addressRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(newPatient));
        when(addressRepository.save(any(Address.class))).thenReturn(existing);

        AddressResponseDto result = addressService.updateAddress(10L, request);

        assertNotNull(result);
        verify(patientRepository).findById(2L);
    }

    @Test
    void updateAddress_differentPatientId_newPatientNotFound_throwsResourceNotFoundException() {
        Patient currentPatient = buildPatient(1L);
        Address existing = buildAddress(10L, currentPatient);
        AddressRequestDto request = buildRequest(99L);

        when(addressRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.updateAddress(10L, request));
    }

    @Test
    void updateAddress_notFound_throwsResourceNotFoundException() {
        when(addressRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.updateAddress(99L, buildRequest(1L)));
    }

    @Test
    void deleteAddress_success() {
        Patient patient = buildPatient(1L);
        Address address = buildAddress(10L, patient);
        when(addressRepository.findById(10L)).thenReturn(Optional.of(address));

        addressService.deleteAddress(10L);

        verify(addressRepository).delete(address);
    }

    @Test
    void deleteAddress_notFound_throwsResourceNotFoundException() {
        when(addressRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.deleteAddress(99L));
    }
}
