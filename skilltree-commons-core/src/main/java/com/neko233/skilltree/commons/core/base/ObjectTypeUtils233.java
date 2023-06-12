package com.neko233.skilltree.commons.core.base;

import com.neko233.skilltree.annotation.MustImplement;
import com.neko233.skilltree.annotation.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ps: 抽象类中的泛型类型参数没有被子类指定，则无法直接获取其具体类型。<br/>
 * 在 Java 中，泛型类型参数只存在于编译时，运行时会被类型擦除。<br>
 *
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public class ObjectTypeUtils233 {

    /**
     * 获取范型列表
     *
     * @param object 对象, 必须是实现类
     * @return 范型 List
     */
    public static List<Type> getGenericTypeList(@MustImplement Object object) {
        if (object == null) {
            return ListUtils233.of();
        }
        Type[] genericInterfaces = object.getClass().getGenericInterfaces();

        if (ArrayUtils233.isEmpty(genericInterfaces)) {
            return ListUtils233.of();
        }
        // fan
        Type genericEventType = genericInterfaces[0];
        // default impl = sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
        ParameterizedType pTypeImpl = (ParameterizedType) genericEventType;
        Type[] actualTypeArguments = pTypeImpl.getActualTypeArguments();
        return Arrays.stream(actualTypeArguments)
                .collect(Collectors.toList());
    }

    /**
     * 获取泛型 class
     *
     * @param object 对象
     * @return Class List
     */
    public static List<Class<?>> getGenericClassList(@MustImplement Object object) {
        return Optional.ofNullable(getGenericTypeList(object)).orElse(ListUtils233.of())
                .stream()
                .map(t -> t instanceof Class ? (Class<?>) t : null)
                .collect(Collectors.toList());
    }

    /**
     * 获取范型列表
     *
     * @param object 对象, 必须是实现类
     * @return 范型 List
     */
    @Nullable
    public static Class<?> getGenericClassNameByHumanIndex(@MustImplement Object object,
                                                         int humanIndex) {
        List<Class<?>> genericTypeList = getGenericClassList(object);
        Class<?> clazz = ListUtils233.getByHumanIndex(genericTypeList, humanIndex);
        if (clazz == null) {
            return null;
        }
        return clazz;
    }
}
