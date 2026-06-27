package com.grupo10.patient_service.controller;

import com.grupo10.patient_service.dto.PatientRequestDto;
import com.grupo10.patient_service.dto.PatientResponseDto;
import com.grupo10.patient_service.service.PatientService;
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
 * Controlador REST para la gestión de pacientes.
 *
 * <p>Expone los endpoints CRUD bajo la ruta base {@code /api/patients}.
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    /**
     * Crea una nueva instancia del controlador con el servicio de pacientes inyectado.
     *
     * @param patientService servicio de lógica de negocio para pacientes
     */
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Crea un nuevo paciente en el sistema.
     *
     * @param request datos del paciente a registrar
     * @return el paciente creado con estado HTTP 201 CREATED
     */
    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@RequestBody PatientRequestDto request) {
        PatientResponseDto response = patientService.createPatient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene la lista de todos los pacientes registrados.
     *
     * @return lista de pacientes con estado HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> response = patientService.getAllPatients();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene un paciente por su identificador.
     *
     * @param id identificador único del paciente
     * @return datos del paciente con estado HTTP 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        PatientResponseDto response = patientService.getPatientById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Actualiza los datos de un paciente existente de forma parcial.
     *
     * <p>Los campos nulos o en blanco en {@code request} son ignorados;
     * {@code idUsuario} y {@code rut} no pueden modificarse.
     *
     * @param id      identificador del paciente a actualizar
     * @param request campos a actualizar
     * @return el paciente actualizado con estado HTTP 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable Long id,
            @RequestBody PatientRequestDto request) {
        PatientResponseDto response = patientService.updatePatient(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Elimina un paciente por su identificador.
     *
     * @param id identificador del paciente a eliminar
     * @return respuesta vacía con estado HTTP 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
