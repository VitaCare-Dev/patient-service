package com.grupo10.patient_service.exception;

/**
 * Excepción lanzada cuando las credenciales proporcionadas son inválidas o no están autorizadas.
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje descriptivo del error de autenticación.
     *
     * @param message descripción del fallo de autenticación
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
