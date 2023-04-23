package com.neko233.toolchain.commons.core.spi;

/**
 * @author SolarisNeko
 * Date on 2022-12-17
 */
public class TestSpiApiImpl implements TestSpiApi {
    @Override
    public String content() {
        return "test";
    }
}
