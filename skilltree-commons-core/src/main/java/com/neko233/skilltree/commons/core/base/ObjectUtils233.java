package com.neko233.skilltree.commons.core.base;

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectUtils233 {

    public static <T> T getOrDefault(T object,
                                     T defaultValue) {
        return object == null ? defaultValue : object;
    }

    public static <T> T getOrDefault(T object,
                                     Supplier<T> defaultValueCreator) {
        if (object != null) {
            return object;
        }
        return defaultValueCreator == null ? null : defaultValueCreator.get();
    }

    public static boolean isAllNotNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnyNull(Object... objects) {
        return !isAllNotNull(objects);
    }

    public static boolean isNotEquals(Object obj1,
                                      Object obj2) {
        return !isEquals(obj1, obj2);
    }

    public static boolean isEquals(Object obj1,
                                   Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    /**
     * 是否是实例
     *
     * @param obj         对象
     * @param targetClass 目标类型
     * @return is same type
     */
    public static boolean isInstanceOf(Object obj,
                                       Class<?> targetClass) {
        if (isAnyNull(obj, targetClass)) {
            return false;
        }
        return targetClass.isInstance(obj);
    }

    /**
     * 是否 不是 class 的实例
     */
    public static boolean isNotInstanceOf(Object obj,
                                          Class<?> targetClass) {
        return !isInstanceOf(obj, targetClass);
    }


}
