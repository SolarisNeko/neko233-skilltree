package com.neko233.toolchain.commons.core.dataStruct.bloomfilter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author SolarisNeko
 * Date on 2023-01-28
 */
public class BloomFilterSimpleTest {

    private final BloomFilterSimple<BloomFilterTestDto> bloomFilterSimple = new BloomFilterSimple<>(2);

    @Before
    @Test
    public void add() {
        bloomFilterSimple.add(new BloomFilterTestDto(1));
        bloomFilterSimple.add(new BloomFilterTestDto(2));
    }

    @Test
    public void mightContain_contains() {
        BloomFilterTestDto value = new BloomFilterTestDto(1);
        boolean isContains = bloomFilterSimple.isMightContains(value);
        assertTrue(isContains);
    }

    @Test
    public void mightContain_notContains() {
        BloomFilterTestDto value = new BloomFilterTestDto(3);
        boolean isContains = bloomFilterSimple.isMightContains(value);
        assertFalse(isContains);
    }

}