package com.grupo10.patient_service.exception;

/**
 * Excepción lanzada cuando un recurso solicitado no es encontrado en el sistema.
 *
 * <p>Utilizada por servicios y manejada por {@link GlobalExceptionHandler}
 * devolviendo HTTP 404 NOT FOUND.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje descriptivo del recurso no encontrado.
     *
     * @param message descripción del recurso ausente
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
