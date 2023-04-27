package com.neko233.skilltree.commons.core.spi;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2022-12-17
 */
public class SpiUtilTest {

    @Test
    public void test() {
        TestSpiApi testSpiApi = SpiUtil.loadFromServiceLoader(TestSpiApi.class);

        Assert.assertEquals("test", testSpiApi.content());
    }

}