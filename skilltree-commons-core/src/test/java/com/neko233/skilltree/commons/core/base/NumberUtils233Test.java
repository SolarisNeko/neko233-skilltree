package com.neko233.skilltree.commons.core.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author SolarisNeko
 * Date on 2023-01-30
 */
public class NumberUtils233Test {

    @Test
    public void addLongIdByLoop_iterator() {
        long aLong = NumberUtils233.addLongIdByLoop(1, 1, 50);
        assertEquals(2, aLong);
    }

    @Test
    public void addLongIdByLoop_first() {
        long aLong = NumberUtils233.addLongIdByLoop(50, 1, 50);
        assertEquals(1, aLong);
    }

    @Test
    public void test_getLongIdByLoop_negative() {
        long aLong = NumberUtils233.getLongIdByLoop(-1, 1, 50);
        assertEquals(50, aLong);
    }

    @Test
    public void test_getLongIdByLoop_succes() {
        long aLong = NumberUtils233.getLongIdByLoop(3, 1, 50);
        assertEquals(3, aLong);
    }
}