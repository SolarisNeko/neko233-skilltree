package com.neko233.toolchain.commons.core.reflect;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils233Test {

    public int demo() {
        return 0;
    }

    @Test
    public void test1() throws InvocationTargetException, IllegalAccessException {
        Method demo = ReflectUtils233.getMethodByName(ReflectUtils233Test.class, "demo");
        Object invoke = demo.invoke(this);
        Assert.assertEquals(0, invoke);
    }


}