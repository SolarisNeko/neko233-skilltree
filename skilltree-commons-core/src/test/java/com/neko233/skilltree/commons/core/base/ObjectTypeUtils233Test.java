package com.neko233.skilltree.commons.core.base;

import com.neko233.skilltree.commons.mockData.MockGenericApi;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ObjectTypeUtils233Test {

    MockGenericData data = new MockGenericData();
    MockErrorGenericData errorGenericData = new MockErrorGenericData();

    @Test
    public void test_other_class_in_interface_use() {
        List<Class<?>> genericTypeList = errorGenericData.getErrorUseGenericClassList();

        Assert.assertEquals(2, genericTypeList.size());
        Assert.assertEquals(MockGenericData.class, genericTypeList.get(0));
        Assert.assertEquals(String.class, genericTypeList.get(1));
    }

    @Test
    public void test_interface_use_default_method() {
        List<Class<?>> genericTypeList = data.getErrorUseGenericClassList();

        Assert.assertEquals(2, genericTypeList.size());
        Assert.assertEquals(Integer.class, genericTypeList.get(0));
        Assert.assertEquals(String.class, genericTypeList.get(1));
    }

    @Test
    public void getGenericTypeList() {
        List<Class<?>> genericTypeList = ObjectTypeUtils233.getGenericClassList(data);
        Assert.assertEquals(2, genericTypeList.size());
    }

    @Test
    public void getGenericClassNameByHumanIndex() {
        Class<?> clazz = ObjectTypeUtils233.getGenericClassNameByHumanIndex(data, 1);
        Assert.assertEquals(Integer.class, clazz);
    }

    @Test
    public void getGenericClassNameByHumanIndex_2() {
        Class<?> clazz = ObjectTypeUtils233.getGenericClassNameByHumanIndex(data, 2);
        Assert.assertEquals(String.class, clazz);
    }

    public static class MockGenericData implements MockGenericApi<Integer, String> {
        @Override
        public String getData(String data) {
            return data;
        }

        @Override
        public String getNumber(Integer integer) {
            return String.valueOf(integer);
        }
    }

    public static class MockErrorGenericData implements MockGenericApi<MockGenericData, String> {
        @Override
        public String getData(String data) {
            return data;
        }

        @Override
        public String getNumber(MockGenericData integer) {
            return String.valueOf(integer);
        }
    }
}