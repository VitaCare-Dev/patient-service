package com.grupo10.patient_service.util;

import com.grupo10.patient_service.dto.PatientRequestDto;
import com.grupo10.patient_service.model.Patient;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUtilTest {

    @Test
    void copyNonNullProperties_copiesNonNullValues() {
        PatientRequestDto source = new PatientRequestDto();
        source.setNombre("Carlos");
        source.setApellidoPaterno("López");
        source.setTelefonoPrincipal("+56911111111");
        source.setFechaNacimiento(LocalDate.of(1990, 5, 20));

        Patient target = new Patient();
        target.setNombre("Juan");
        target.setApellidoPaterno("Pérez");
        target.setTelefonoPrincipal("+56900000000");

        UpdateUtil.copyNonNullProperties(source, target);

        assertEquals("Carlos", target.getNombre());
        assertEquals("López", target.getApellidoPaterno());
        assertEquals("+56911111111", target.getTelefonoPrincipal());
        assertEquals(LocalDate.of(1990, 5, 20), target.getFechaNacimiento());
    }

    @Test
    void copyNonNullProperties_doesNotOverwriteWithNull() {
        PatientRequestDto source = new PatientRequestDto();
        source.setNombre("Carlos");

        Patient target = new Patient();
        target.setNombre("Juan");
        target.setApellidoPaterno("Pérez");

        UpdateUtil.copyNonNullProperties(source, target);

        assertEquals("Carlos", target.getNombre());
        assertEquals("Pérez", target.getApellidoPaterno());
    }

    @Test
    void copyNonNullProperties_doesNotOverwriteWithBlankString() {
        PatientRequestDto source = new PatientRequestDto();
        source.setNombre("   ");
        source.setApellidoPaterno("Rodríguez");

        Patient target = new Patient();
        target.setNombre("Juan");
        target.setApellidoPaterno("Pérez");

        UpdateUtil.copyNonNullProperties(source, target);

        assertEquals("Juan", target.getNombre());
        assertEquals("Rodríguez", target.getApellidoPaterno());
    }

    @Test
    void privateConstructor_isCovered() throws Exception {
        Constructor<UpdateUtil> constructor = UpdateUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }
}
