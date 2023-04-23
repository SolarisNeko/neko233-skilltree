package com.neko233.toolchain.commons.core.base;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class CollectionUtils233Test {

    @Test
    public void ofList() {
        List<String> name = CollectionUtils233.ofList("name", null);
        assertEquals(name.size(), 2);
    }

    @Test
    public void ofSet() {
        Set<String> strings = CollectionUtils233.ofSet("name", "name");
        assertEquals(1, strings.size());
    }
}