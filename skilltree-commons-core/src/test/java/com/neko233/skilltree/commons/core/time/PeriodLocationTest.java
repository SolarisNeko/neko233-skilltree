package com.neko233.skilltree.commons.core.time;

import com.neko233.skilltree.commons.core.base.DateTimeUtils233;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2022-12-15
 */
public class PeriodLocationTest {

    @Test
    public void testPeriod() {
        long startMs = DateTimeUtils233.getMs("2022-01-01", "yyyy-MM-dd");
        long endMs = DateTimeUtils233.getMs("2023-01-01", "yyyy-MM-dd");
        long nowMs = DateTimeUtils233.getMs("2022-12-31", "yyyy-MM-dd");

        PeriodLocation periodLocation = Period.calculatePeriod(nowMs, startMs, endMs, 1, TimeUnit.DAYS);

        Assert.assertTrue(periodLocation.getIsInPeriod());
        Assert.assertEquals(Long.valueOf(86400 * 1000), periodLocation.getPeriodMs());
        Assert.assertEquals(Long.valueOf(366), periodLocation.getAllPeriodCount());
        Assert.assertEquals(Long.valueOf(365), periodLocation.getCurrentPeriodCount());
    }

    @Test
    public void testErrorTimestamp() {
        long startMs = DateTimeUtils233.getMs("2022-01-01", "yyyy-MM-dd");
        long endMs = DateTimeUtils233.getMs("1970-01-01", "yyyy-MM-dd");
        long nowMs = DateTimeUtils233.getMs("2022-12-31", "yyyy-MM-dd");

        PeriodLocation periodLocation = Period.calculatePeriod(nowMs, startMs, endMs, 1, TimeUnit.DAYS);

        Assert.assertFalse(periodLocation.getIsInPeriod());
        Assert.assertEquals(periodLocation.getPeriodMs(), Long.valueOf(86400 * 1000));
        Assert.assertEquals(periodLocation.getAllPeriodCount(), Long.valueOf(0));
        Assert.assertEquals(periodLocation.getCurrentPeriodCount(), Long.valueOf(0));
    }

}