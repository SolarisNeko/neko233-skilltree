package com.neko233.toolchain.validator;

import com.neko233.toolchain.common.scanner.PackageScanner;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ValidateApiFactory {

    private static final Map<Class, ValidateApi> validateMap = new ConcurrentHashMap<>();

    public static ValidateApi choose(Annotation annotation) {
        if (annotation == null) {
            return null;
        }
        return validateMap.get(annotation.annotationType());
    }

    public static void register(Class annotationType, ValidateApi validateApi) {
        validateMap.merge(annotationType, validateApi, (v1, v2) -> v2);
    }

    public static void unregister(Class annotationType) {
        validateMap.remove(annotationType);
    }

    public static void unregisterAll() {
        validateMap.clear();
    }

    /**
     * @param classPath 类路径
     */
    public static void registerByScanPackage(String classPath) {
        Set<Class<?>> classes = PackageScanner.listClazz(classPath, true, ValidateApi.class::isAssignableFrom);
        for (Class<?> clazz : classes) {
            if (!ValidateApi.class.isAssignableFrom(clazz)) {
                continue;
            }
            if (clazz.isInterface()) {
                continue;
            }

            ValidateApi o = null;
            try {
                Constructor<?> constructor = clazz.getConstructor();
                o = (ValidateApi) constructor.newInstance();
            } catch (Throwable t) {
                log.error("register class to validate cache error. className = {}", clazz.getName(), t);
                continue;
            }
            if (o == null) {
                continue;
            }
            register(o.getAnnotationType(), o);
        }
    }
}
