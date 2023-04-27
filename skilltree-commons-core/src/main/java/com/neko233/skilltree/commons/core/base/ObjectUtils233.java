package com.neko233.skilltree.commons.core.base;

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
}
