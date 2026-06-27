package com.grupo10.patient_service.model;

import lombok.Getter;

/**
 * Enumeración que define las reglas médicas de umbrales vitales por enfermedad crónica y grupo etario.
 *
 * <p>Cada entrada codifica los límites clínicos recomendados para glucosa, presión arterial
 * y temperatura según la enfermedad y si el paciente es adulto (18–64 años) o mayor (65+).
 * Estas reglas son consumidas por {@link com.grupo10.patient_service.service.ChronicDiseaseService}
 * al registrar una nueva enfermedad crónica en un paciente.
 */
@Getter
public enum MedicalRules {

    /** Regla para diabetes en pacientes adultos (18–64 años). */
    DIABETES_ADULTO("Diabetes", 18, 64, 180, 70, 140, 90, 37.5),
    /** Regla para diabetes en pacientes mayores (65–120 años). */
    DIABETES_MAYOR("Diabetes", 65, 120, 200, 70, 150, 90, 37.5),

    /** Regla para hipertensión en pacientes adultos (18–64 años). */
    HIPERTENSION_ADULTO("Hipertensión", 18, 64, 140, 70, 140, 90, 37.5),
    /** Regla para hipertensión en pacientes mayores (65–120 años). */
    HIPERTENSION_MAYOR("Hipertensión", 65, 120, 150, 80, 150, 90, 37.5),

    /** Regla para enfermedad cardíaca en pacientes adultos (18–64 años). */
    ENFERMEDAD_CARDIACA_ADULTO("Enfermedad Cardíaca", 18, 64, 140, 70, 130, 80, 37.5),
    /** Regla para enfermedad cardíaca en pacientes mayores (65–120 años). */
    ENFERMEDAD_CARDIACA_MAYOR("Enfermedad Cardíaca", 65, 120, 140, 80, 140, 85, 37.5),

    /** Regla para enfermedad renal en pacientes adultos (18–64 años). */
    ENFERMEDAD_RENAL_ADULTO("Enfermedad Renal", 18, 64, 140, 70, 130, 80, 37.5),
    /** Regla para enfermedad renal en pacientes mayores (65–120 años). */
    ENFERMEDAD_RENAL_MAYOR("Enfermedad Renal", 65, 120, 150, 80, 140, 85, 37.5),

    /** Regla para obesidad en pacientes adultos (18–64 años). */
    OBESIDAD_ADULTO("Obesidad", 18, 64, 140, 70, 140, 90, 37.5),
    /** Regla para obesidad en pacientes mayores (65–120 años). */
    OBESIDAD_MAYOR("Obesidad", 65, 120, 150, 80, 150, 90, 37.5);

    /** Nombre de la enfermedad asociada a esta regla. */
    private final String nombreEnfermedad;
    /** Edad mínima (inclusive) del rango etario cubierto por esta regla. */
    private final Integer edadMin;
    /** Edad máxima (inclusive) del rango etario cubierto por esta regla. */
    private final Integer edadMax;
    /** Glucosa máxima en sangre permitida (mg/dL). */
    private final Integer glucosaMax;
    /** Glucosa mínima en sangre permitida (mg/dL). */
    private final Integer glucosaMin;
    /** Presión sistólica máxima permitida (mmHg). */
    private final Integer sistolicaMax;
    /** Presión diastólica máxima permitida (mmHg). */
    private final Integer diastolicaMax;
    /** Temperatura corporal máxima permitida (°C). */
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

    /**
     * Busca la regla médica aplicable según el nombre de la enfermedad y la edad del paciente.
     *
     * <p>La comparación del nombre de enfermedad es insensible a mayúsculas/minúsculas.
     * Devuelve {@code null} si no existe una regla para la combinación indicada.
     *
     * @param nombreEnfermedad nombre de la enfermedad crónica a buscar
     * @param edad             edad actual del paciente en años
     * @return la regla médica correspondiente, o {@code null} si no se encuentra
     */
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
