package com.grupo10.patient_service.exception;

/**
 * Excepción lanzada cuando se viola una regla de negocio de carácter general.
 */
public class BusinessLogicException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje descriptivo del error.
     *
     * @param message descripción del error de negocio
     */
    public BusinessLogicException(String message) {
        super(message);
    }
}
