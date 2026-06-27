package com.grupo10.patient_service.controller;

import com.grupo10.patient_service.dto.DiseaseRequestDto;
import com.grupo10.patient_service.dto.DiseaseResponseDto;
import com.grupo10.patient_service.service.DiseaseService;
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
 * Controlador REST para la gestión del catálogo de enfermedades crónicas.
 *
 * <p>Expone los endpoints CRUD bajo la ruta base {@code /api/diseases}.
 */
@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    private final DiseaseService diseaseService;

    /**
     * Crea una nueva instancia del controlador con el servicio de enfermedades inyectado.
     *
     * @param diseaseService servicio de lógica de negocio para enfermedades
     */
    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    /**
     * Crea una nueva enfermedad en el catálogo del sistema.
     *
     * @param request datos de la enfermedad a registrar
     * @return la enfermedad creada con estado HTTP 201 CREATED
     */
    @PostMapping
    public ResponseEntity<DiseaseResponseDto> createDisease(@RequestBody DiseaseRequestDto request) {
        DiseaseResponseDto response = diseaseService.createDisease(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene la lista de todas las enfermedades registradas en el catálogo.
     *
     * @return lista de enfermedades con estado HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<DiseaseResponseDto>> getAllDiseases() {
        List<DiseaseResponseDto> response = diseaseService.getAllDiseases();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene una enfermedad del catálogo por su identificador.
     *
     * @param id identificador único de la enfermedad
     * @return datos de la enfermedad con estado HTTP 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiseaseResponseDto> getDiseaseById(@PathVariable Long id) {
        DiseaseResponseDto response = diseaseService.getDiseaseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Actualiza los datos de una enfermedad existente de forma parcial.
     *
     * @param id      identificador de la enfermedad a actualizar
     * @param request campos a actualizar
     * @return la enfermedad actualizada con estado HTTP 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<DiseaseResponseDto> updateDisease(@PathVariable Long id,
            @RequestBody DiseaseRequestDto request) {
        DiseaseResponseDto response = diseaseService.updateDisease(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina una enfermedad del catálogo por su identificador.
     *
     * @param id identificador de la enfermedad a eliminar
     * @return respuesta vacía con estado HTTP 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        diseaseService.deleteDisease(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
