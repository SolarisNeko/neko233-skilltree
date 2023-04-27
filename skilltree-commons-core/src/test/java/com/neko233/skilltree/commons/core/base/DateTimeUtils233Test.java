package com.neko233.skilltree.commons.core.base;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class DateTimeUtils233Test {

    @Test
    public void isLeapYear() {
        boolean leapYear = DateTimeUtils233.isLeapYear(2020);
        Assert.assertTrue(leapYear);
    }


    @Test
    public void parse() {
        LocalDateTime parse = DateTimeUtils233.parse("2020-01-01", "yyyy-MM-dd HH:mm:ss");
        Assert.assertTrue(parse != null);
    }

    @Test
    public void parse2() {
        LocalDateTime parse = DateTimeUtils233.parse("2020-01-01", "yyyy-MM-dd HH:mm:ss");
        Assert.assertTrue(parse != null);
    }

    @Test
    public void testToString() {
        String s = DateTimeUtils233.toString(LocalDateTime.now(), "yyyy-MM-dd");
//        System.out.println(s);
    }


    @Test
    public void testGetMs() {
        long ms = DateTimeUtils233.getMs(LocalDateTime.now());
    }

    @Test
    public void age() {
        LocalDateTime parse = DateTimeUtils233.parse("2000-01-01", "yyyy-MM-dd");
        LocalDateTime parse2 = DateTimeUtils233.parse("2020-01-01", "yyyy-MM-dd");
        int age = DateTimeUtils233.age(parse, parse2);
//        System.out.println(age);
    }

    @Test
    public void age_ms() {
        // 1262275200000L = 2010-01-01 00:00:00
        // 1673967503000L = 2023-01-17 22:58:23
        int age = DateTimeUtils233.age(1262275200000L, 1673967503000L);
        Assert.assertEquals(13, age);

    }

}