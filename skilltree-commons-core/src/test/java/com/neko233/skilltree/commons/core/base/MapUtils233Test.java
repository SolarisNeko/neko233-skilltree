package com.neko233.skilltree.commons.core.base;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class MapUtils233Test {

    @Test
    public void of() {
        Map<String, Object> of = MapUtils233.of("name", null, "age", 18);
        assertEquals(of.size(), 2);
    }
}