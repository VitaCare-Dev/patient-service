package com.grupo10.patient_service.constants;

import lombok.experimental.UtilityClass;

/**
 * Constantes de mensajes de error utilizadas en las excepciones de negocio del microservicio.
 *
 * <p>Centraliza los textos de error para mantener consistencia en las respuestas de la API
 * y facilitar su mantenimiento.
 */
@UtilityClass
public class GlobalConstants {

    /** Prefijo del mensaje cuando un paciente no es encontrado por ID. */
    public static final String PATIENT_NOT_FOUND = "Paciente no encontrado con el ID: ";

    /** Mensaje cuando se intenta registrar un RUT que ya existe en el sistema. */
    public static final String DUPLICATE_RUT = "Ya existe un paciente registrado con el RUT ingresado";

    /** Mensaje cuando un usuario ya tiene un paciente asociado. */
    public static final String DUPLICATE_USER_PATIENT = "El usuario ya tiene un paciente registrado";

    /** Prefijo del mensaje cuando un usuario no es encontrado por ID. */
    public static final String USER_NOT_FOUND = "No existe un usuario con el ID: ";

    /** Prefijo del mensaje cuando una dirección no es encontrada por ID. */
    public static final String ADDRESS_NOT_FOUND = "Dirección no encontrada con el ID: ";

    /** Prefijo del mensaje cuando una enfermedad no es encontrada por ID. */
    public static final String DISEASE_NOT_FOUND = "Enfermedad no encontrada con el ID: ";
}
