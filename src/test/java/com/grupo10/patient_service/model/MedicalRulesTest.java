package com.grupo10.patient_service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicalRulesTest {

    @Test
    void findByDiseaseAndAge_diabetesAdulto() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Diabetes", 40);

        assertNotNull(result);
        assertEquals(MedicalRules.DIABETES_ADULTO, result);
        assertEquals(180, result.getGlucosaMax());
        assertEquals(70, result.getGlucosaMin());
        assertEquals(140, result.getSistolicaMax());
        assertEquals(90, result.getDiastolicaMax());
        assertEquals(37.5, result.getTemperaturaMax());
    }

    @Test
    void findByDiseaseAndAge_diabetesMayor() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Diabetes", 70);

        assertNotNull(result);
        assertEquals(MedicalRules.DIABETES_MAYOR, result);
        assertEquals(200, result.getGlucosaMax());
    }

    @Test
    void findByDiseaseAndAge_hipertensionAdulto() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Hipertensión", 30);

        assertNotNull(result);
        assertEquals(MedicalRules.HIPERTENSION_ADULTO, result);
    }

    @Test
    void findByDiseaseAndAge_hipertensionMayor() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Hipertensión", 80);

        assertNotNull(result);
        assertEquals(MedicalRules.HIPERTENSION_MAYOR, result);
    }

    @Test
    void findByDiseaseAndAge_enfermedadCardiacaAdulto() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Enfermedad Cardíaca", 50);

        assertNotNull(result);
        assertEquals(MedicalRules.ENFERMEDAD_CARDIACA_ADULTO, result);
    }

    @Test
    void findByDiseaseAndAge_enfermedadCardiacaMayor() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Enfermedad Cardíaca", 75);

        assertNotNull(result);
        assertEquals(MedicalRules.ENFERMEDAD_CARDIACA_MAYOR, result);
    }

    @Test
    void findByDiseaseAndAge_enfermedadRenalAdulto() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Enfermedad Renal", 45);

        assertNotNull(result);
        assertEquals(MedicalRules.ENFERMEDAD_RENAL_ADULTO, result);
    }

    @Test
    void findByDiseaseAndAge_enfermedadRenalMayor() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Enfermedad Renal", 90);

        assertNotNull(result);
        assertEquals(MedicalRules.ENFERMEDAD_RENAL_MAYOR, result);
    }

    @Test
    void findByDiseaseAndAge_obesidadAdulto() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Obesidad", 35);

        assertNotNull(result);
        assertEquals(MedicalRules.OBESIDAD_ADULTO, result);
    }

    @Test
    void findByDiseaseAndAge_obesidadMayor() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Obesidad", 68);

        assertNotNull(result);
        assertEquals(MedicalRules.OBESIDAD_MAYOR, result);
    }

    @Test
    void findByDiseaseAndAge_unknownDisease_returnsNull() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("EnfermedadDesconocida", 40);

        assertNull(result);
    }

    @Test
    void findByDiseaseAndAge_ageOutOfRange_returnsNull() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("Diabetes", 5);

        assertNull(result);
    }

    @Test
    void findByDiseaseAndAge_caseInsensitive() {
        MedicalRules result = MedicalRules.findByDiseaseAndAge("DIABETES", 40);

        assertNotNull(result);
        assertEquals(MedicalRules.DIABETES_ADULTO, result);
    }

    @Test
    void getters_returnCorrectValues() {
        MedicalRules regla = MedicalRules.DIABETES_ADULTO;

        assertEquals("Diabetes", regla.getNombreEnfermedad());
        assertEquals(18, regla.getEdadMin());
        assertEquals(64, regla.getEdadMax());
    }
}
