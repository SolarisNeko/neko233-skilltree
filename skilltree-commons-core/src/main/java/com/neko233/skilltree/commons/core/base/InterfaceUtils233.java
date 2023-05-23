package com.neko233.skilltree.commons.core.base;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class InterfaceUtils233 {

    /**
     * 获取所有上层接口
     *
     * @param clazz 类
     * @return 集合
     */
    public static Set<Class<?>> getAllInterfaces(Class<?> clazz) {
        Set<Class<?>> interfaces = new HashSet<>();

        // Get direct interfaces of the class
        Type[] directInterfaces = clazz.getInterfaces();
        for (Type directInterface : directInterfaces) {
            interfaces.add((Class<?>) directInterface);

            // Recursively get parent interfaces
            Set<Class<?>> allInterfaces = InterfaceUtils233.getAllInterfaces((Class<?>) directInterface);
            interfaces.addAll(allInterfaces);
        }

        return interfaces;
    }


    /**
     * class -> 生成 interface
     * 将所有 public method 自动提炼成 interface
     *
     * @param clazz 类
     * @return 方法
     */
    public static String generateInterfaceMethod(Class<?> clazz) {
        StringBuilder interfaceBuilder = new StringBuilder();
        interfaceBuilder.append("public interface ").append(clazz.getSimpleName()).append("Interface {\n");

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                continue;
            }
            if (Modifier.isPublic(modifiers)) {
                // 方法签名
                String methodSignature = getMethodSignature(method);
                interfaceBuilder.append("\t").append(methodSignature).append(";\n");
            }
        }

        interfaceBuilder.append("}");
        return interfaceBuilder.toString();
    }

    /**
     * 获取方法签名
     *
     * @param method 方法
     * @return 方法签名
     */
    public static String getMethodSignature(Method method) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(Modifier.toString(method.getModifiers() & ~Modifier.ABSTRACT)).append(" ");
        signatureBuilder.append(method.getReturnType().getSimpleName()).append(" ");
        signatureBuilder.append(method.getName()).append("(");

        // 方法参数
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> parameterType = parameter.getType();
            String parameterName = parameter.getName();

            String parameterTypeSimpleName = parameterType.getSimpleName();

            signatureBuilder.append(parameterTypeSimpleName).append(" ").append(parameterName);
            if (i < parameters.length - 1) {
                signatureBuilder.append(", ");
            }
        }
        signatureBuilder.append(")");
        return signatureBuilder.toString();
    }


}
