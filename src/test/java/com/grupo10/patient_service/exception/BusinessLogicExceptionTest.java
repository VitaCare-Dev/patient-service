package com.grupo10.patient_service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessLogicExceptionTest {

    @Test
    void constructor_setsMessage() {
        String message = "Error de lógica de negocio";

        BusinessLogicException ex = new BusinessLogicException(message);

        assertEquals(message, ex.getMessage());
    }

    @Test
    void isRuntimeException() {
        BusinessLogicException ex = new BusinessLogicException("error");

        assertInstanceOf(RuntimeException.class, ex);
    }
}
