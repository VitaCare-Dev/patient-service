package com.grupo10.patient_service.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO de salida para respuestas de error estandarizadas.
 *
 * <p>Utilizado por {@link com.grupo10.patient_service.exception.GlobalExceptionHandler}
 * para devolver mensajes de error uniformes con código HTTP y marca de tiempo.
 */
@Data
public class ErrorResponseDto {
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
