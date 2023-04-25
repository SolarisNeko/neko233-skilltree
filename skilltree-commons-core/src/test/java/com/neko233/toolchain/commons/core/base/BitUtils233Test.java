package com.neko233.toolchain.commons.core.base;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


public class BitUtils233Test {

    @Test
    public void test() {
        Map<Integer, String> integerStringMap = BitUtils233.bitPrettyOutput(1);
        Assert.assertEquals("00000000,00000000,00000000,00000001", integerStringMap.get(1));
    }

}