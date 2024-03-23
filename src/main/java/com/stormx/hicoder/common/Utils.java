package com.stormx.hicoder.common;

public class Utils {
    public static boolean isValidSortField(String sortField, Class<?> entityClass) {
        try {
            entityClass.getDeclaredField(sortField);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
