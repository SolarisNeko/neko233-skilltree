package com.neko233.skilltree.scheduler.client.constant;

/**
 * @author SolarisNeko
 * Date on 2023-05-01
 */
public enum ScheduleTaskType {

    /**
     * 只执行一次
     */
    ONCE,

    /**
     * 间隔执行
     */
    INTERVAL,

    /**
     * 在固定的 DateTime 下执行
     */
    AT_DATETIME,

    /**
     * CRON 驱动
     */
    CRON,

    /**
     * 分布式调用桩 | 受调用者
     */
    DISTRIBUTE_SYSTEM_STUB,

    ;

}
