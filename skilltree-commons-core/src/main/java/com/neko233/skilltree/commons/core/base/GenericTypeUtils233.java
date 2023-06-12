package com.neko233.skilltree.commons.core.base;

import java.lang.reflect.*;

public class GenericTypeUtils233 {
    
    public static Class<?> getClassGenericType(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        return extractClassFromType(genericSuperclass);
    }
    
    public static Class<?> getMethodReturnType(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        return extractClassFromType(genericReturnType);
    }
    
    public static Class<?>[] getMethodParameterTypes(Method method) {
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        Class<?>[] parameterTypes = new Class<?>[genericParameterTypes.length];
        for (int i = 0; i < genericParameterTypes.length; i++) {
            parameterTypes[i] = extractClassFromType(genericParameterTypes[i]);
        }
        return parameterTypes;
    }
    
    public static Class<?> getFieldGenericType(Field field) {
        Type genericType = field.getGenericType();
        return extractClassFromType(genericType);
    }
    
    private static Class<?> extractClassFromType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class) {
                    return (Class<?>) typeArgument;
                }
            }
        } else if (type instanceof Class) {
            return (Class<?>) type;
        }
        return null;
    }
}
