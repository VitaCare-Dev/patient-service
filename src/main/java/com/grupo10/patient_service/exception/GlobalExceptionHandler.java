package com.grupo10.patient_service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.grupo10.patient_service.dto.ErrorResponseDto;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para la API REST.
 *
 * <p>Intercepta las excepciones de negocio lanzadas por los servicios y devuelve
 * respuestas HTTP estandarizadas usando {@link com.grupo10.patient_service.dto.ErrorResponseDto}.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja {@link ResourceNotFoundException} devolviendo HTTP 404 NOT FOUND.
     *
     * @param ex excepción capturada
     * @return respuesta de error con código 404 y mensaje descriptivo
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja {@link DuplicateResourceException} devolviendo HTTP 409 CONFLICT.
     *
     * @param ex excepción capturada
     * @return respuesta de error con código 409 y mensaje descriptivo
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateResource(DuplicateResourceException ex) {
        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Maneja {@link UserNotFoundException} devolviendo HTTP 404 NOT FOUND.
     *
     * @param ex excepción capturada
     * @return respuesta de error con código 404 y mensaje descriptivo
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja {@link InvalidCredentialsException} devolviendo HTTP 401 UNAUTHORIZED.
     *
     * @param ex excepción capturada
     * @return respuesta de error con código 401 y mensaje descriptivo
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentialsException(InvalidCredentialsException ex) {

        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
