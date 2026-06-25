package com.grupo10.patient_service.model;

import lombok.Getter;

@Getter
public enum MedicalRules {

    DIABETES_ADULTO("Diabetes", 18, 64, 180, 70, 140, 90, 37.5),
    DIABETES_MAYOR("Diabetes", 65, 120, 200, 70, 150, 90, 37.5),

    HIPERTENSION_ADULTO("Hipertensión", 18, 64, 140, 70, 140, 90, 37.5),
    HIPERTENSION_MAYOR("Hipertensión", 65, 120, 150, 80, 150, 90, 37.5),

    ENFERMEDAD_CARDIACA_ADULTO("Enfermedad Cardíaca", 18, 64, 140, 70, 130, 80, 37.5),
    ENFERMEDAD_CARDIACA_MAYOR("Enfermedad Cardíaca", 65, 120, 140, 80, 140, 85, 37.5),

    ENFERMEDAD_RENAL_ADULTO("Enfermedad Renal", 18, 64, 140, 70, 130, 80, 37.5),
    ENFERMEDAD_RENAL_MAYOR("Enfermedad Renal", 65, 120, 150, 80, 140, 85, 37.5),

    OBESIDAD_ADULTO("Obesidad", 18, 64, 140, 70, 140, 90, 37.5),
    OBESIDAD_MAYOR("Obesidad", 65, 120, 150, 80, 150, 90, 37.5);

    private final String nombreEnfermedad;
    private final Integer edadMin;
    private final Integer edadMax;
    private final Integer glucosaMax;
    private final Integer glucosaMin;
    private final Integer sistolicaMax;
    private final Integer diastolicaMax;
    private final Double temperaturaMax;

    MedicalRules(String nombreEnfermedad, Integer edadMin, Integer edadMax,
            Integer glucosaMax, Integer glucosaMin, Integer sistolicaMax,
            Integer diastolicaMax, Double temperaturaMax) {
        this.nombreEnfermedad = nombreEnfermedad;
        this.edadMin = edadMin;
        this.edadMax = edadMax;
        this.glucosaMax = glucosaMax;
        this.glucosaMin = glucosaMin;
        this.sistolicaMax = sistolicaMax;
        this.diastolicaMax = diastolicaMax;
        this.temperaturaMax = temperaturaMax;
    }

    public static MedicalRules findByDiseaseAndAge(String nombreEnfermedad, Integer edad) {
        for (MedicalRules regla : values()) {
            if (regla.nombreEnfermedad.equalsIgnoreCase(nombreEnfermedad) &&
                    edad >= regla.edadMin && edad <= regla.edadMax) {
                return regla;
            }
        }
        return null;
    }
}
