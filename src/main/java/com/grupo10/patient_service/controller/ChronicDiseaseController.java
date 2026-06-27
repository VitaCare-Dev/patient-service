package com.grupo10.patient_service.controller;

import com.grupo10.patient_service.dto.MedicalThresholdResponseDto;
import com.grupo10.patient_service.dto.PatientDiseaseRequestDto;
import com.grupo10.patient_service.service.ChronicDiseaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la gestión de enfermedades crónicas de pacientes y sus umbrales médicos.
 *
 * <p>Expone los endpoints bajo la ruta base {@code /api/chronic-diseases} para registrar
 * enfermedades a pacientes y consultar los umbrales vitales calculados automáticamente.
 */
@RestController
@RequestMapping("/api/chronic-diseases")
public class ChronicDiseaseController {

    private final ChronicDiseaseService chronicDiseaseService;

    /**
     * Crea una nueva instancia del controlador con el servicio de enfermedades crónicas inyectado.
     *
     * @param chronicDiseaseService servicio de lógica de negocio para enfermedades crónicas
     */
    public ChronicDiseaseController(ChronicDiseaseService chronicDiseaseService) {
        this.chronicDiseaseService = chronicDiseaseService;
    }

    /**
     * Registra una enfermedad crónica a un paciente y actualiza sus umbrales médicos.
     *
     * <p>Si el paciente ya tiene umbrales registrados, los nuevos valores se fusionan
     * adoptando los límites más restrictivos entre las enfermedades acumuladas.
     *
     * @param request DTO con los identificadores del paciente y la enfermedad
     * @return respuesta vacía con estado HTTP 201 CREATED
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerPatientDisease(@RequestBody PatientDiseaseRequestDto request) {
        chronicDiseaseService.registerPatientDisease(
                request.getIdPaciente(),
                request.getIdEnfermedad());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Obtiene los umbrales médicos personalizados de un paciente.
     *
     * @param idPaciente identificador del paciente
     * @return umbrales médicos del paciente con estado HTTP 200 OK
     */
    @GetMapping("/thresholds/{idPaciente}")
    public ResponseEntity<MedicalThresholdResponseDto> getPatientThresholds(@PathVariable Long idPaciente) {
        MedicalThresholdResponseDto response = chronicDiseaseService.getPatientThresholds(idPaciente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
