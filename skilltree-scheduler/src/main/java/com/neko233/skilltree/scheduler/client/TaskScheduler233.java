package com.neko233.skilltree.scheduler.client;

import com.neko233.datetime.DateTime233;
import com.neko233.datetime.cron.CronExpression;
import com.neko233.skilltree.commons.core.annotation.NotNull;
import com.neko233.skilltree.commons.core.base.CollectionUtils233;
import com.neko233.skilltree.commons.core.scanner.PackageScanner;
import com.neko233.skilltree.scheduler.client.annotation.ScheduleTask;
import com.neko233.skilltree.scheduler.client.annotation.ScheduleTaskClass;
import com.neko233.skilltree.scheduler.client.constant.ScheduleTaskType;
import com.neko233.skilltree.scheduler.client.helper.TaskSchedulerHelper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 任务调度器
 *
 * @author SolarisNeko on 2023-05-01
 */
@Slf4j
public class TaskScheduler233 {

    public static final String DATETIME_FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    private final ScheduledExecutorService executorService;
    private final Map<String, ScheduledFuture<?>> scheduleTaskNameMap = new ConcurrentHashMap<>();
    private final TaskStubRegistry taskStubRegistry;

    public static TaskScheduler233 create(ScheduledExecutorService scheduler) {
        return new TaskScheduler233(scheduler);
    }

    private TaskScheduler233(ScheduledExecutorService executorService) {
        this.taskStubRegistry = new TaskStubRegistry();
        this.executorService = executorService;
    }

    public static TaskScheduler233 createDefault() {
        ScheduledExecutorService jdkScheduler = Executors.newScheduledThreadPool(1);
        return create(jdkScheduler);
    }

    /**
     * must need no-args constructor scheduler
     */
    public void scanAppAndSchedule(Class<?> appClass) {
        Package aPackage = appClass.getPackage();
        Set<Class<?>> taskClassList = PackageScanner.scanClass(aPackage.getName(), true, (clazz) -> {
            return clazz.isAnnotationPresent(ScheduleTaskClass.class);
        });

        if (CollectionUtils233.isEmpty(taskClassList)) {
            log.info("[TaskScheduler] nothing to schedule by TaskScheduler233. App className = {}", appClass.getName());
            return;
        }

        List<Object> collect = taskClassList.stream()
                .map(clazz -> {
                    try {
                        Constructor<?> constructor = clazz.getConstructor();
                        constructor.setAccessible(true);
                        return constructor.newInstance();
                    } catch (Throwable e) {
                        log.error("instantiate clazz by no-args constructor error. className = {}", clazz.getName());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        for (Object o : collect) {
            scheduleByAnnotation(o);
        }
    }


    public void scheduleByAnnotation(List<Object> objectList) {
        for (Object o : objectList) {
            scheduleByAnnotation(o);
        }
    }

    public void unregisterSchedule(Object object) {

    }

    /**
     * 开始调度
     *
     * @param object 对象
     */
    public void scheduleByAnnotation(Object object) {
        Class<?> aClass = object.getClass();
        if (isNotSchedulerTaskClass(aClass)) {
            log.error("[TaskScheduler] object not have @annotation = {}, className = {}",
                    ScheduleTaskClass.class.getName(), aClass.getName());
            return;
        }


        String className = aClass.getName();


        long currentMs = System.currentTimeMillis();

        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {

            if (isNotScheduleTaskMethod(method)) {
                continue;
            }

            @NotNull final ScheduleTask scheduleTask = getScheduleTaskAnnotationByMethod(method);

            DateTime233 startDateTime = DateTime233.of(scheduleTask.startDateTime(),
                    DATETIME_FORMAT_YMD_HMS);
            DateTime233 endDateTime = DateTime233.of(scheduleTask.endDateTime(),
                    DATETIME_FORMAT_YMD_HMS);
            long endMs = endDateTime.originalTimeMs();

            LocalDateTime startJdkDateTime = startDateTime.toLocalDateTime();

            if (currentMs >= endMs) {
                continue;

            }
            ScheduleTaskType taskType = scheduleTask.type();

            // 只调度一次
            if (taskType == ScheduleTaskType.ONCE) {
                // 调度一次
                scheduleOnce(object,
                        method,
                        scheduleTask);
                continue;
            }

            // 固定时间下
            if (taskType == ScheduleTaskType.AT_DATETIME) {
                DateTime233 atDateTime = DateTime233.of(scheduleTask.atDateTime(),
                        DATETIME_FORMAT_YMD_HMS);
                // 到某个时间点调度一次
                scheduleAtDateTime(scheduleTask,
                        object,
                        method,
                        atDateTime.toLocalDateTime());
                continue;
            }

            // 固定间隔
            if (taskType == ScheduleTaskType.INTERVAL) {

                int interval = scheduleTask.interval();
                if (interval <= 0) {
                    continue;
                }
                long intervalMs = scheduleTask.intervalTimeUnit()
                        .toMillis(interval);
                // 每隔多少秒调度一次
                scheduleByFixed(object,
                        method,
                        scheduleTask,
                        startJdkDateTime,
                        intervalMs);
                continue;
            }

            // cron
            if (taskType == ScheduleTaskType.CRON) {


                scheduleByCron(scheduleTask,
                        object,
                        method);

                continue;
            }

            // distribute system
            if (taskType == ScheduleTaskType.DISTRIBUTE_SYSTEM_STUB) {

                registerTaskToTaskRegistry(scheduleTask,
                        object,
                        method);

                continue;
            }
        }
    }

    private void registerTaskToTaskRegistry(ScheduleTask scheduleTask,
                                            Object object,
                                            Method method) {

        String taskName = TaskSchedulerHelper.getTaskNameOrDefault(scheduleTask, object, method);

        taskStubRegistry.register(taskName, object, method);

    }

    private boolean isNotSchedulerTaskClass(Class<?> aClass) {
        return !isSchedulerTaskClass(aClass);
    }

    private static boolean isSchedulerTaskClass(Class<?> aClass) {
        return aClass.isAnnotationPresent(ScheduleTaskClass.class);
    }

    private void scheduleByCron(ScheduleTask scheduleTask,
                                Object object,
                                Method method) {

        final String cron = scheduleTask.cron();

        Class<?> className = object.getClass();
        String methodName = method.getName();
        CronExpression cronExpression;
        try {
            cronExpression = new CronExpression(cron);
        } catch (ParseException e) {
            log.error("parse scheduler cron error. className = {}, methodName = {}, cron = {}",
                    className,
                    methodName,
                    cron);
            return;
        }

        Date nextDate = cronExpression.getNextValidTimeAfter(DateTime233.now().toDate());
        long nextMs = nextDate.getTime();

        log.debug("cron next execute at = {}",
                DateTime233.of(nextMs));

        long currentMs = System.currentTimeMillis();
        long delayMs = Math.max(0,
                (nextMs - currentMs));
        if (delayMs <= 0) {
            return;
        }

        String taskName = TaskSchedulerHelper.getTaskNameOrDefault(scheduleTask, object, method);

        ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {
                    try {
                        method.invoke(object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    // 先取消之前的
                    unregisterTaskInSchedule(taskName);

                    scheduleByCron(scheduleTask, object,
                            method);
                },
                delayMs,
                TimeUnit.MILLISECONDS);

        registerTask(scheduledFuture, scheduleTask, object, method);
    }

    private void scheduleOnce(Object object,
                              Method method,
                              ScheduleTask scheduleTask) {
        int delayTime = scheduleTask.onceDelayTime();
        if (delayTime <= 0) {
            // 直接执行
            executorService.execute(() -> {
                try {
                    method.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            return;
        }

        long delayMs = scheduleTask.onceDelayTimeUnit()
                .toMillis(delayTime);

        String taskName = TaskSchedulerHelper.getTaskNameOrDefault(scheduleTask, object, method);
        ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {
                    String methodName = method.getName();
                    String className = object.getClass()
                            .getName();

                    try {
                        method.invoke(object);
                    } catch (Throwable e) {
                        log.error("Scheduler 反射调度方法报错. className = {}, methodName = {}, error",
                                className,
                                methodName,
                                e);
                    } finally {
                        unregisterTaskInSchedule(taskName);
                    }
                },
                delayMs,
                TimeUnit.MILLISECONDS);

        registerTaskByTaskName(scheduledFuture, taskName);
    }

    private void unregisterTask(Class<?> clazz) {
        if (isNotSchedulerTaskClass(clazz)) {
            return;
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (isNotScheduleTaskMethod(method)) {
                continue;
            }
            ScheduleTask annotation = getScheduleTaskAnnotationByMethod(method);

            String taskNameOrDefault = TaskSchedulerHelper.getTaskNameOrDefault(annotation, clazz, method);
            unregisterTask(taskNameOrDefault);
        }

    }

    private static ScheduleTask getScheduleTaskAnnotationByMethod(Method method) {
        return method.getAnnotation(ScheduleTask.class);
    }

    private boolean isNotScheduleTaskMethod(Method method) {
        return !isScheduleTaskMethod(method);
    }

    private static boolean isScheduleTaskMethod(Method method) {
        return method.isAnnotationPresent(ScheduleTask.class);
    }

    /**
     * 调度中取消任务
     *
     * @param taskName 任务名
     */
    private void unregisterTaskInSchedule(String taskName) {
        ScheduledFuture<?> scheduledFuture = scheduleTaskNameMap.get(taskName);
        if (scheduledFuture == null) {
            return;
        }

        scheduledFuture.cancel(false);
    }

    private void unregisterTask(String taskName) {
        ScheduledFuture<?> scheduledFuture = scheduleTaskNameMap.get(taskName);
        if (scheduledFuture == null) {
            return;
        }

        scheduledFuture.cancel(true);
    }

    private void registerTask(ScheduledFuture<?> scheduledFuture,
                              ScheduleTask scheduleTask,
                              Object object,
                              Method method) {
        String taskName = TaskSchedulerHelper.getTaskNameOrDefault(scheduleTask, object, method);

        registerTaskByTaskName(scheduledFuture, taskName);
    }

    private void registerTaskByTaskName(ScheduledFuture<?> scheduledFuture,
                                        String taskName) {

        scheduleTaskNameMap.merge(taskName, scheduledFuture, (v1, v2) -> {
            log.debug("previous Schedule Future will cancel. taskName = {}", taskName);
            v1.cancel(false);
            return v2;
        });
    }


    /**
     * 调度方法在固定时间
     *
     * @param scheduleTask 调度任务
     * @param object       对象
     * @param method       方法
     * @param startTime    开始时间
     */
    private void scheduleAtDateTime(ScheduleTask scheduleTask,
                                    Object object,
                                    Method method,
                                    LocalDateTime startTime) {
        String taskName = TaskSchedulerHelper.getTaskNameOrDefault(scheduleTask, object, method);
        long delay = Duration.between(LocalDateTime.now(), startTime).getSeconds();
        if (delay < 0) {
            log.info("[TaskScheduler233] at dateTime task have past. 已经过了时间, 不需要执行. taskName = {} have done",
                    taskName);
            return;
        }
        ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {
                    try {
                        method.invoke(object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                },
                delay,
                TimeUnit.SECONDS);

        registerTask(scheduledFuture, scheduleTask, object, method);

    }

    private void scheduleByFixed(Object object,
                                 Method method,
                                 ScheduleTask scheduleTask,
                                 LocalDateTime startTime,
                                 long intervalMs) {
        long initialDelay = Duration.between(LocalDateTime.now(), startTime)
                .getSeconds();

        ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(() -> {
                    try {
                        method.invoke(object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                },
                initialDelay,
                intervalMs,
                TimeUnit.MILLISECONDS);

        registerTask(scheduledFuture, scheduleTask, object, method);
    }

    public Collection<String> getAllRegisterTaskList() {
        return this.scheduleTaskNameMap.keySet();
    }

    public TaskStubRegistry getTaskStubRegistry() {
        return taskStubRegistry;
    }
}

