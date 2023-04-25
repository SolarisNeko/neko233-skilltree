package com.neko233.toolchain.commons.core.base;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-01-19
 */
public class StringUtils233Test {

    @Test
    public void test_toBigCamelCaseUpper() {
        String systemUser = StringUtils233.toBigCamelCaseUpper("SystemUser");
        Assert.assertEquals("SYSTEM_USER", systemUser);
    }

    @Test
    public void test_toBigCamelCaseLower() {
        String systemUser = StringUtils233.toBigCamelCaseLower("SystemUser");
        Assert.assertEquals("system_user", systemUser);
    }

    @Test
    public void test_isNumber_success() {
        boolean number = StringUtils233.isNumber("1234");
        Assert.assertTrue(number);
    }

    @Test
    public void test_isNumber_failure() {
        boolean number = StringUtils233.isNumber("1234a");
        Assert.assertFalse(number);
    }

    @Test
    public void test_toStringObject() {
        String systemUser = StringUtils233.stringTextToStringObject("\"SystemUser\"");
        Assert.assertEquals("SystemUser", systemUser);
    }

}