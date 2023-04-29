package com.neko233.skilltree.commons.core.base;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public class ObjectTypeUtils233 {

    /**
     * 获取范型列表
     *
     * @param object 对象
     * @return 范型 List
     */
    public static List<Type> getGenericTypeList(Object object) {
        if (object == null) {
            return ListUtils233.of();
        }
        Type[] genericInterfaces = object.getClass().getGenericInterfaces();

        if (ArrayUtils233.isEmpty(genericInterfaces)) {
            return ListUtils233.of();
        }
        // fan
        Type genericEventType = genericInterfaces[0];
        Type[] actualTypeArguments = ((ParameterizedTypeImpl) genericEventType).getActualTypeArguments();
        return Arrays.stream(actualTypeArguments)
                .collect(Collectors.toList());
    }

    public static String getGenericClassNameByHumanIndex(Object object, int humanIndex) {
        List<Type> genericTypeList = getGenericTypeList(object);
        Type byHuman = ListUtils233.getByHumanIndex(genericTypeList, humanIndex);
        if (byHuman == null) {
            return null;
        }
        return byHuman.getTypeName();
    }
}
