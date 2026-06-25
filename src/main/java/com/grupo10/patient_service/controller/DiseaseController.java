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

@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @PostMapping
    public ResponseEntity<DiseaseResponseDto> createDisease(@RequestBody DiseaseRequestDto request) {
        DiseaseResponseDto response = diseaseService.createDisease(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DiseaseResponseDto>> getAllDiseases() {
        List<DiseaseResponseDto> response = diseaseService.getAllDiseases();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseResponseDto> getDiseaseById(@PathVariable Long id) {
        DiseaseResponseDto response = diseaseService.getDiseaseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiseaseResponseDto> updateDisease(@PathVariable Long id,
            @RequestBody DiseaseRequestDto request) {
        DiseaseResponseDto response = diseaseService.updateDisease(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        diseaseService.deleteDisease(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
