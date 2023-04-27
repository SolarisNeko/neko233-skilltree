package com.neko233.skilltree.commons.core.time;

/**
 * @author SolarisNeko on 2022-12-15
 **/
public interface PeriodApi {

    /**
     * 立即刷新
     */
    void refreshNow();

    /**
     * 不刷新. 当前数据记录, 是否在周期中
     *
     * @return 是否在周期中
     */
    boolean isInPeriod();

    /**
     * 先刷新，再判断是否再周期中。 用于判断当前状态.
     *
     * @return 是否周期中
     */
    default boolean isInPeriodAfterRefresh() {
        refreshNow();
        return isInPeriod();
    }


}
