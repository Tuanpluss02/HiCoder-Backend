package com.stormx.hicoder.common;

import java.lang.reflect.Field;

public class Utils {
    public static boolean isValidSortField(String sortField, Class<?> entityClass) {
        try {
            Field field = entityClass.getDeclaredField(sortField);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
