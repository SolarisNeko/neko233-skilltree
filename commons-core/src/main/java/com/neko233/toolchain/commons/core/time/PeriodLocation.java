package com.neko233.toolchain.commons.core.time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * PeriodLocation 代表一个连续的小周期, 并且在这个小周期中, 划分出一个个小时间段.
 * 如果需要多个周期, 请将其转为 List<PeriodLocation>
 *
 * @author SolarisNeko
 * Date on 2022-12-15
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodLocation implements PeriodApi {

    private Boolean isInPeriod; // 是否在周期中

    private Long currentMs; // 当前毫秒

    private Long periodMs; // 周期间隔, 毫秒
    private Long allStartMs; // 全局开始, 毫秒
    private Long allEndMs; // 全局结束, 毫秒

    private Long currentPeriodStartMs; // 当前周期开始, 毫秒
    private Long currentPeriodEndMs; // 当前周期结束, 毫秒

    private Long allPeriodCount; // 总共, 多少次周期
    private Long currentPeriodCount; // 当前, 第几次周期

    @Override
    public void refreshNow() {
        PeriodLocation newOne;
        try {
            newOne = Period.calculatePeriod(this.allStartMs, this.allEndMs, periodMs, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Period calculate happen error. ", e);
            return;
        }
        // state
        this.isInPeriod = newOne.isInPeriod;
        // current part
        this.currentMs = newOne.currentMs;
        this.currentPeriodStartMs = newOne.currentPeriodEndMs;
        this.currentPeriodEndMs = newOne.currentPeriodEndMs;
        // period
        this.currentPeriodCount = newOne.currentPeriodCount;
    }

    @Override
    public boolean isInPeriod() {
        return this.isInPeriod;
    }
}


