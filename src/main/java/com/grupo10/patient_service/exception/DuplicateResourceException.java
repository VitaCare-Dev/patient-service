package com.grupo10.patient_service.exception;

/**
 * Excepción lanzada cuando se intenta registrar un recurso que ya existe en el sistema.
 *
 * <p>Casos de uso: RUT duplicado o un usuario que ya tiene un paciente asociado.
 */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje descriptivo del conflicto.
     *
     * @param message descripción del recurso duplicado
     */
    public DuplicateResourceException(String message) {
        super(message);
    }

}
