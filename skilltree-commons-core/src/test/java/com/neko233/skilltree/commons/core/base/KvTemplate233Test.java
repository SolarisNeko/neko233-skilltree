package com.neko233.skilltree.commons.core.base;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class KvTemplate233Test {

    @Test
    public void test_base() {
        // 重复替换
        String input = "Hello, ${name}! Welcome to ${city}, ${name}.";

        String build = KvTemplate233.builder(input)
                .put("name", "SolarisNeko")
                .put("city", "BlackHole")
                .build();

        Assert.assertEquals(build, "Hello, SolarisNeko! Welcome to BlackHole, SolarisNeko.");
    }


    @Test
    public void test_parse() {
        String input = "Hello, ${name}! Welcome to ${city}.";
        List<String> placeholders = KvTemplate233.parsePlaceHolder(input);

        boolean v1 = placeholders.contains("name");
        boolean v2 = placeholders.contains("city");

        Assert.assertTrue(v1 && v2);
    }


}