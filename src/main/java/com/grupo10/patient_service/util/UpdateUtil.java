package com.grupo10.patient_service.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class UpdateUtil {

    private UpdateUtil() {
        // Constructor privado para evitar instanciación
    }

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

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