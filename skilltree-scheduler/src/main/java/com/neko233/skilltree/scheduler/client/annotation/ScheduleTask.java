package com.neko233.skilltree.scheduler.client.annotation;


import com.neko233.skilltree.scheduler.client.constant.ScheduleTaskType;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2023-05-01
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduleTask {

    /**
     * @return 调度任务名字 | 唯一标识
     */
    String taskName() default "";

    /**
     * @return 调度类型
     */
    ScheduleTaskType type();

    /**
     * 只调度一次的延迟时间
     *
     * @return 延迟时间，单位另算
     */
    int onceDelayTime() default 0;

    /**
     * @return 只调度一次的延迟时间单位
     */
    TimeUnit onceDelayTimeUnit() default TimeUnit.SECONDS;

    /**
     * @return 时间间隔多久, 单位另算. | <= 0 不会调度
     */
    int interval() default 0;

    /**
     * @return 间隔单位
     */
    TimeUnit intervalTimeUnit() default TimeUnit.SECONDS;


    /**
     * @return yyyy-MM-dd HH:mm:ss | 开始时间
     */
    String startDateTime() default "1970-01-01 00:00:00";


    /**
     * @return yyyy-MM-dd HH:mm:ss | 结束时间
     */
    String endDateTime() default "5000-01-01 00:00:00";


    /**
     * @return 指定某个时间进行调度
     */
    String atDateTime() default "";

    /**
     * @return cron 调度 | 6 choose style, like * * * * * ?
     */
    String cron() default "";


}
