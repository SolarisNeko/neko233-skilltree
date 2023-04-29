package com.neko233.skilltree.commons.core.base;

import java.util.Objects;

public class ObjectUtils233 {

    public static boolean allNotNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnyNull(Object... objects) {
        return !allNotNull(objects);
    }

    public static <T> T getOrDefault(T object, T defaultValue) {
        return object == null ? defaultValue : object;
    }

    public static boolean isNotEquals(Object obj1, Object obj2) {
        return !isEquals(obj1, obj2);
    }

    public static boolean isEquals(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }
}
