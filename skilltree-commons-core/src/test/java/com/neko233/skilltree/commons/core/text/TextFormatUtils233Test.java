package com.neko233.skilltree.commons.core.text;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author SolarisNeko
 * Date on 2022-12-17
 */
public class TextFormatUtils233Test {

    @Test
    public void test() {
        String log = "2022-11-01 INFO myLogger - asdflkajsdflkjasdf";
        String logFormat = "${date} ${level} ${logger} - ${msg}";

        Map<String, String> formatMap = TextFormatUtils233.formatLine(log, logFormat);

        Assert.assertEquals("INFO", formatMap.getOrDefault("level", ""));
        Assert.assertEquals("myLogger", formatMap.getOrDefault("logger", ""));
        Assert.assertEquals("asdflkajsdflkjasdf", formatMap.getOrDefault("msg", ""));
    }

    @Test
    public void test2() {
        String log = "orderPay(demo=1)";
        String logFormat = "${functionName}(${param})";

        Map<String, String> formatMap = TextFormatUtils233.lineFormatMapping(log, logFormat, true);

        Assert.assertEquals("orderPay", formatMap.getOrDefault("functionName", ""));
        Assert.assertEquals("demo=1", formatMap.getOrDefault("param", ""));
    }
}
