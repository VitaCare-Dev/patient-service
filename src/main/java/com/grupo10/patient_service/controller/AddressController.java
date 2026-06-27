package com.grupo10.patient_service.controller;

import com.grupo10.patient_service.dto.AddressRequestDto;
import com.grupo10.patient_service.dto.AddressResponseDto;
import com.grupo10.patient_service.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la gestión de direcciones de pacientes.
 *
 * <p>Expone los endpoints CRUD bajo la ruta base {@code /api/addresses},
 * incluyendo la consulta de direcciones filtradas por paciente.
 */
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    /**
     * Crea una nueva instancia del controlador con el servicio de direcciones inyectado.
     *
     * @param addressService servicio de lógica de negocio para direcciones
     */
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Crea una nueva dirección asociada a un paciente.
     *
     * @param request datos de la dirección a registrar
     * @return la dirección creada con estado HTTP 201 CREATED
     */
    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto request) {
        AddressResponseDto response = addressService.createAddress(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene la lista de todas las direcciones registradas en el sistema.
     *
     * @return lista de direcciones con estado HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        List<AddressResponseDto> response = addressService.getAllAddresses();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene una dirección por su identificador.
     *
     * @param id identificador único de la dirección
     * @return datos de la dirección con estado HTTP 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable Long id) {
        AddressResponseDto response = addressService.getAddressById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene todas las direcciones asociadas a un paciente específico.
     *
     * @param idPaciente identificador del paciente
     * @return lista de direcciones del paciente con estado HTTP 200 OK
     */
    @GetMapping("/patient/{idPaciente}")
    public ResponseEntity<List<AddressResponseDto>> getAddressesByPatientId(@PathVariable Long idPaciente) {
        List<AddressResponseDto> response = addressService.getAddressesByPatientId(idPaciente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Actualiza los datos de una dirección existente de forma parcial.
     *
     * <p>Si se indica un {@code idPaciente} diferente al actual, la dirección
     * será reasignada al nuevo paciente.
     *
     * @param id      identificador de la dirección a actualizar
     * @param request campos a actualizar
     * @return la dirección actualizada con estado HTTP 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(@PathVariable Long id,
            @RequestBody AddressRequestDto request) {
        AddressResponseDto response = addressService.updateAddress(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina una dirección por su identificador.
     *
     * @param id identificador de la dirección a eliminar
     * @return respuesta vacía con estado HTTP 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
