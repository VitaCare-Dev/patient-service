package com.grupo10.patient_service.exception;

/**
 * Excepción lanzada cuando el usuario referenciado por {@code idUsuario} no existe en el sistema.
 *
 * <p>Manejada por {@link GlobalExceptionHandler} devolviendo HTTP 404 NOT FOUND.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje descriptivo del usuario no encontrado.
     *
     * @param message descripción del usuario ausente
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
