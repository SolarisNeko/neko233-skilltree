package com.neko233.skilltree.commons.core.base;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author SolarisNeko
 * Date on 2023-06-12
 */
public class GenericTypeUtils233Test {

    class Example<T> {

        public List<T> getList() {
            return null;
        }
    }


    @Test
    public void getClassGenericType() throws NoSuchMethodException {
        Method getList = Example.class.getMethod("getList");

        Class<?> listType = GenericTypeUtils233.getMethodReturnType(getList);
        // Output: class java.lang.Object
        System.out.println(listType);
    }

    @Test
    public void getMethodReturnType() {
    }

    @Test
    public void getMethodParameterTypes() {
    }

    @Test
    public void getFieldGenericType() {
    }
}