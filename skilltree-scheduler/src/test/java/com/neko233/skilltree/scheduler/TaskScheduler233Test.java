package com.neko233.skilltree.scheduler;

import com.neko233.datetime.DateTime233;
import com.neko233.skilltree.commons.core.base.CollectionUtils233;
import com.neko233.skilltree.commons.core.base.MapUtils233;
import com.neko233.skilltree.scheduler.client.TaskScheduler233;
import com.neko233.skilltree.scheduler.client.TaskStubRegistry;
import com.neko233.skilltree.scheduler.client.annotation.ScheduleTask;
import com.neko233.skilltree.scheduler.client.annotation.ScheduleTaskClass;
import com.neko233.skilltree.scheduler.client.annotation.ScheduleTaskParamText;
import com.neko233.skilltree.scheduler.client.constant.ScheduleTaskType;
import com.neko233.skilltree.scheduler.client.dto.TaskStubInvokeResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ScheduleTaskClass
public class TaskScheduler233Test {

    public static final TaskScheduler233 taskScheduler = TaskScheduler233.createDefault();

    public static final AtomicInteger counter = new AtomicInteger();

    @Test
    public void test_by_scanPackage() throws InterruptedException {
        taskScheduler.scanAppAndSchedule(TaskScheduler233Test.class);

        TimeUnit.SECONDS.sleep(5);

        boolean isNotEmptyRegisterTask = CollectionUtils233.isNotEmpty(taskScheduler.getAllRegisterTaskList());
        Assert.assertTrue(isNotEmptyRegisterTask);

        TaskStubRegistry taskStubRegistry = taskScheduler.getTaskStubRegistry();
        Map<String, Object> taskNameToObjectMap = taskStubRegistry.getTaskNameToObjectMap();
        Assert.assertTrue(MapUtils233.isNotEmpty(taskNameToObjectMap));

        TaskStubInvokeResult taskStubInvokeResult = taskStubRegistry.invokeTask("demo", "test");
        boolean isSuccess = taskStubInvokeResult.isSuccess();
        Assert.assertTrue(isSuccess);

    }


    //    @Test
    public void test() throws InterruptedException {
        TaskScheduler233Test taskScheduler233Test = new TaskScheduler233Test();
        taskScheduler.scheduleByAnnotation(taskScheduler233Test);

        TimeUnit.SECONDS.sleep(10);


    }

    // 单次
    @ScheduleTask(type = ScheduleTaskType.ONCE, onceDelayTime = 3, onceDelayTimeUnit = TimeUnit.SECONDS)
    public void once() {
        System.out.println("Hello, world!");
    }

    // 间隔
    @ScheduleTask(type = ScheduleTaskType.INTERVAL, interval = 2, intervalTimeUnit = TimeUnit.SECONDS)
    public void interval() {
        log.info("interval {}",
                counter.getAndIncrement());
    }

    // 指定时间点
    @ScheduleTask(type = ScheduleTaskType.AT_DATETIME, atDateTime = "2023-05-01 00:35:00")
    public void atDateTime() {
        log.info("atDateTime. execute at {}", DateTime233.now());
    }

    // cron
    @ScheduleTask(type = ScheduleTaskType.CRON, cron = "*/3 * * * * ?")
    public void cron() {
        log.info("cron. execute at {}", DateTime233.now());
    }

    // cron
    @ScheduleTask(taskName = "demo", type = ScheduleTaskType.DISTRIBUTE_SYSTEM_STUB)
    public void stub(String nothing, @ScheduleTaskParamText String params) {
        log.info("分布式 stub. execute at {}, 接收到的参数 = {}, {}", DateTime233.now(), nothing, params);
    }
}
