package com.grupo10.patient_service.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Utilidad para la copia selectiva de propiedades entre objetos (source → target).
 *
 * <p>Ignora los campos nulos o en blanco del origen, preservando los valores
 * existentes en el destino. Los campos {@code idUsuario} y {@code rut} son siempre
 * excluidos para proteger su inmutabilidad en entidades de paciente.
 */
public class UpdateUtil {

    private UpdateUtil() {
    }

    /**
     * Copia las propiedades no nulas y no vacías del objeto origen al objeto destino.
     *
     * <p>Los campos {@code idUsuario} y {@code rut} son excluidos independientemente de su valor.
     *
     * @param source objeto origen del que se leen los valores
     * @param target objeto destino sobre el que se aplican los valores copiados
     */
    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * Obtiene los nombres de las propiedades que deben ser excluidas de la copia.
     *
     * <p>Se excluyen las propiedades cuyo valor es {@code null} o una cadena en blanco,
     * así como {@code idUsuario} y {@code rut} de forma fija.
     *
     * @param source objeto del que se inspeccionan las propiedades
     * @return arreglo con los nombres de las propiedades a excluir
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());

            if (srcValue == null || (srcValue instanceof String string && string.trim().isEmpty())) {
                emptyNames.add(pd.getName());
            }
        }

        emptyNames.add("idUsuario");
        emptyNames.add("rut");

        return emptyNames.toArray(new String[0]);
    }
}