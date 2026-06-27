package com.grupo10.patient_service.controller;

import com.grupo10.patient_service.dto.AddressRequestDto;
import com.grupo10.patient_service.dto.AddressResponseDto;
import com.grupo10.patient_service.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @Test
    void createAddress_returns201() {
        AddressRequestDto request = new AddressRequestDto();
        AddressResponseDto response = new AddressResponseDto();
        when(addressService.createAddress(request)).thenReturn(response);

        ResponseEntity<AddressResponseDto> result = addressController.createAddress(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getAllAddresses_returns200() {
        List<AddressResponseDto> list = List.of(new AddressResponseDto());
        when(addressService.getAllAddresses()).thenReturn(list);

        ResponseEntity<List<AddressResponseDto>> result = addressController.getAllAddresses();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void getAddressById_returns200() {
        AddressResponseDto response = new AddressResponseDto();
        when(addressService.getAddressById(1L)).thenReturn(response);

        ResponseEntity<AddressResponseDto> result = addressController.getAddressById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getAddressesByPatientId_returns200() {
        List<AddressResponseDto> list = List.of(new AddressResponseDto());
        when(addressService.getAddressesByPatientId(1L)).thenReturn(list);

        ResponseEntity<List<AddressResponseDto>> result = addressController.getAddressesByPatientId(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void updateAddress_returns200() {
        AddressRequestDto request = new AddressRequestDto();
        AddressResponseDto response = new AddressResponseDto();
        when(addressService.updateAddress(1L, request)).thenReturn(response);

        ResponseEntity<AddressResponseDto> result = addressController.updateAddress(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void deleteAddress_returns204() {
        doNothing().when(addressService).deleteAddress(1L);

        ResponseEntity<Void> result = addressController.deleteAddress(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(addressService).deleteAddress(1L);
    }
}
