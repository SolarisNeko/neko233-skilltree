package com.neko233.skilltree.commons.core.base;

import com.neko233.skilltree.commons.core.pool.DataSizePool;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class DataSizeUtils233Test {

    @Test
    public void testByte() {
        String s = DataSizeUtils233.toHumanFormatByByte(1024);
        Assert.assertEquals("1.00 Byte", s);
    }

    @Test
    public void testKB() {
        String s = DataSizeUtils233.toHumanFormatByByte(1024 * 100);
        Assert.assertEquals("100.00 KB", s);
    }

    @Test
    public void testMB() {
        String s = DataSizeUtils233.toHumanFormatByByte(1024 * 1024 * 2);
        Assert.assertEquals("2.00 MB", s);
    }

    @Test
    public void testGB() {
        BigDecimal size = DataSizeUtils233.calculateDataSize(5, DataSizePool.GB);
        String s = DataSizeUtils233.toHumanFormatByByte(size);
        Assert.assertEquals("5.00 GB", s);
    }

    /**
     * 正向翻译
     */
    @Test
    public void testCalculateSize() {
        BigDecimal size = DataSizeUtils233.calculateDataSize(5, DataSizePool.MB);
        Assert.assertEquals("5242880.0", size.toString());
    }
}