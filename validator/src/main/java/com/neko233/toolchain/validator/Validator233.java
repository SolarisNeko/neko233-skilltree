package com.neko233.toolchain.validator;


import com.neko233.toolchain.common.reflect.ReflectUtils233;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解校验器
 *
 * @author SolarisNeko on 2022-12-30
 */
public class Validator233 {

    private static boolean isInit = false;
    public static final Map<Class<?>, Map<Field, List<ValidateApi>>> classValidateCache = new ConcurrentHashMap<>();

    public static ValidateContext validate(Object object) {
        if (!isInit) {
            initByLock();
        }
        if (object == null) {
            return ValidateContext.builder()
                    .isOk(false)
                    .reason("object is null")
                    .build();
        }

        Map<Field, List<ValidateApi>> fieldValidateApiMap = classValidateCache.get(object.getClass());
        if (fieldValidateApiMap == null) {
            createValidateApi(object);
            fieldValidateApiMap = classValidateCache.get(object.getClass());
        }

        boolean isOk = true;
        String reason = null;
        for (Map.Entry<Field, List<ValidateApi>> fieldValidateApiEntry : fieldValidateApiMap.entrySet()) {
            Field field = fieldValidateApiEntry.getKey();
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                return ValidateContext.builder()
                        .isOk(false)
                        .reason("reflect get field value error")
                        .throwable(e)
                        .build();
            }
            for (ValidateApi validateApi : fieldValidateApiEntry.getValue()) {
                Class annotationType = validateApi.getAnnotationType();
                if (annotationType == null) {
                    continue;
                }
                try {
                    Annotation anno = field.getAnnotation(annotationType);
                    isOk = validateApi.validateOk(anno, value);
                    if (!isOk) {
                        reason = validateApi.getReason(anno, field.getName(), value);
                    }
                } catch (Throwable t) {
                    return ValidateContext.builder()
                            .isOk(false)
                            .reason(String.format("happen error. fieldName = `%s`", field.getName()))
                            .throwable(t)
                            .build();
                }
            }

        }
        return ValidateContext.builder()
                .isOk(isOk)
                .reason(reason)
                .build();
    }

    /**
     * 初始化原生校验器
     */
    private static void initByLock() {
        synchronized (Validator233.class) {
            if (isInit) {
                return;
            }
            isInit = true;
            registerByScan(Validator233.class.getPackage().getName());
        }
    }

    public static void registerByScan(String packagePath) {
        ValidateApiFactory.registerByScanPackage(packagePath);
    }

    private static synchronized void createValidateApi(Object build) {
        Map<Field, List<ValidateApi>> fieldValidateApiMap = classValidateCache.get(build.getClass());
        if (fieldValidateApiMap != null) {
            return;
        }
        List<Field> allFields = ReflectUtils233.getAllFieldsRecursive(build);

        Map<Field, List<ValidateApi>> fieldValidateMap = new HashMap<>();
        for (Field field : allFields) {
            field.setAccessible(true);
            for (Annotation annotation : field.getAnnotations()) {
                ValidateApi api = ValidateApiFactory.choose(annotation);
                if (api == null) {
                    continue;
                }

                fieldValidateMap.merge(field, Collections.singletonList(api), (v1, v2) -> {
                    v1.addAll(v2);
                    return v1;
                });
            }
        }
        classValidateCache.put(build.getClass(), fieldValidateMap);
    }


}
