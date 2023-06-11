package com.neko233.skilltree.commons.core.base;

import org.junit.Assert;
import org.junit.Test;

public class ObjectUtils233Test {

    public static class TestObject {
        public int userId;

        public TestObject(int userId) {
            this.userId = userId;
        }
    }

    public static class TestObject2 {
        public int userId;

        public TestObject2(int userId) {
            this.userId = userId;
        }
    }

    @Test
    public void allNotNull() {
        boolean allNotNull = ObjectUtils233.isAllNotNull("abc", "def");
        Assert.assertTrue(allNotNull);

        boolean allNotNull_false = ObjectUtils233.isAllNotNull("abc", null);
        Assert.assertFalse(allNotNull_false);
    }

    @Test
    public void isAnyNull() {

        boolean anyNull_false = ObjectUtils233.isAnyNull("abc", "def");
        Assert.assertFalse(anyNull_false);

        boolean allNotNull_true = ObjectUtils233.isAnyNull("abc", null);
        Assert.assertTrue(allNotNull_true);
    }

    @Test
    public void getOrDefault() {
    }

    @Test
    public void isNotEquals() {
    }

    @Test
    public void isEquals() {
        boolean equals = ObjectUtils233.isEquals("abc", "abc");
        Assert.assertTrue(equals);
    }

    @Test
    public void isInstanceOf() {
        TestObject testObject = new TestObject(1);
        boolean isSamType = ObjectUtils233.isInstanceOf(testObject, TestObject.class);
        Assert.assertTrue(isSamType);
    }

    @Test
    public void isNotInstanceOf() {

        TestObject testObject = new TestObject(1);
        boolean isNotSameType = ObjectUtils233.isNotInstanceOf(testObject, TestObject2.class);
        Assert.assertTrue(isNotSameType);
    }
}