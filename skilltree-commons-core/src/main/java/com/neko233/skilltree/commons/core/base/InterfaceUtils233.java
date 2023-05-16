package com.neko233.skilltree.commons.core.base;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.ClassUtils.getAllInterfaces;

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

}
