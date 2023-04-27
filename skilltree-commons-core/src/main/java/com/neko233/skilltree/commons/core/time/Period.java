package com.neko233.skilltree.commons.core.time;

import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2022-12-15
 */
public interface Period {


    /**
     * 计算周期 [allStartMs, allEndMs], 以 interval TimeUnit 间隔计算出一个周期 + 附带上当前所处周期
     *
     * @param interval 多久间隔调度一次, 单位: 秒数
     * @param timeUnit 时间单位
     * @return PeriodLocation
     */
    static PeriodLocation calculatePeriod(long allStartMs,
                                          long allEndMs,
                                          Number interval,
                                          TimeUnit timeUnit) {
        long currentMs = System.currentTimeMillis();
        return calculatePeriod(currentMs, allStartMs, allEndMs, interval, timeUnit);
    }

    static PeriodLocation calculatePeriod(long currentMs,
                                          long allStartMs,
                                          long allEndMs,
                                          Number interval,
                                          TimeUnit timeUnit) {
        long periodMs = timeUnit.toMillis(interval.longValue());

        long middleMs = allEndMs - allStartMs;

        long allPeriodCount = middleMs / periodMs;

        // current not in [allStartMs, allEndMs]
        if (currentMs > allEndMs || currentMs < allStartMs) {
            return PeriodLocation.builder()
                    .isInPeriod(false)
                    .currentMs(currentMs)
                    .periodMs(periodMs)
                    .allStartMs(allStartMs)
                    .allEndMs(allEndMs)
                    // current
                    .currentPeriodStartMs(0L)
                    .currentPeriodEndMs(0L)
                    // period meta
                    .allPeriodCount(0L)
                    .currentPeriodCount(0L)
                    .build();
        }

        long currentPeriodCount = (currentMs - allStartMs) / periodMs;

        return PeriodLocation.builder()
                .isInPeriod(true)
                .currentMs(currentMs)
                .periodMs(periodMs)
                .allStartMs(allStartMs)
                .allEndMs(allEndMs)
                // current
                .currentPeriodStartMs(allStartMs + currentPeriodCount * periodMs)
                .currentPeriodEndMs(allStartMs + (currentPeriodCount + 1) * periodMs)
                // period meta
                .allPeriodCount(allPeriodCount + 1)
                .currentPeriodCount(currentPeriodCount + 1)
                .build();
    }

}
