package com.neko233.skilltree.commons.core.base;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class FieldUtils233 {


    /**
     * 获取对象所有字段的值
     *
     * @param obj 对象
     * @return 字段 : 值
     */
    public static Map<Field, Object> getFieldNameToValue(Object obj) {
        if (obj == null) {
            return MapUtils233.of();
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        return getFieldNameToValue(obj, Arrays.asList(declaredFields));
    }

    /**
     * 根据字段名, 获取 Map 值
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @return fieldName : value Map
     */
    public static Map<Field, Object> getFieldNameToValue(Object obj,
                                                         List<String> fieldName) {
        if (CollectionUtils233.isEmpty(fieldName)) {
            return MapUtils233.of();
        }
        if (obj == null) {
            return MapUtils233.of();
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        List<Field> filterList = Arrays.stream(declaredFields)
                .filter(f -> fieldName.contains(f.getName()))
                .collect(Collectors.toList());
        return getFieldNameToValue(obj, filterList);
    }

    /**
     * 获取局部字段的值
     *
     * @param obj    对象
     * @param fields 字段
     * @return 值
     */
    public static Map<Field, Object> getFieldNameToValue(Object obj,
                                                         Collection<Field> fields) {
        Map<Field, Object> map = new HashMap<>(fields.size());
        for (Field field : fields) {
            Object fieldValue = getFieldQuiet(obj, field);
            map.put(field, fieldValue);
        }
        return map;
    }

    /**
     * 安全获取字段
     *
     * @param obj   对象
     * @param field 字段
     * @return value / null
     */
    private static Object getFieldQuiet(Object obj,
                                        Field field) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            log.error("get field value error, field = {}", field.getName(), e);
            return null;
        }
    }

}
