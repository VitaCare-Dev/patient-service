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

@RestController
@RequestMapping("/api/chronic-diseases")
public class ChronicDiseaseController {

    private final ChronicDiseaseService chronicDiseaseService;

    public ChronicDiseaseController(ChronicDiseaseService chronicDiseaseService) {
        this.chronicDiseaseService = chronicDiseaseService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerPatientDisease(@RequestBody PatientDiseaseRequestDto request) {
        chronicDiseaseService.registerPatientDisease(
                request.getIdPaciente(),
                request.getIdEnfermedad());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/thresholds/{idPaciente}")
    public ResponseEntity<MedicalThresholdResponseDto> getPatientThresholds(@PathVariable Long idPaciente) {
        MedicalThresholdResponseDto response = chronicDiseaseService.getPatientThresholds(idPaciente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
