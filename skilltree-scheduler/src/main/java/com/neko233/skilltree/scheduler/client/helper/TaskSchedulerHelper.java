package com.neko233.skilltree.scheduler.client.helper;

import com.neko233.skilltree.annotation.NotNull;
import com.neko233.skilltree.scheduler.client.annotation.ScheduleTask;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

public class TaskSchedulerHelper {


    @NotNull
    public static String getTaskNameOrDefault(ScheduleTask scheduleTask,
                                               Class<?> clazz,
                                               Method method) {
        String taskName = scheduleTask.taskName();
        if (StringUtils.isBlank(taskName)) {
            taskName = clazz.getName() + "::" + method.getName();
        }
        return taskName;
    }


    /**
     * 调度任务名
     *
     * @param scheduleTask 调度任务
     * @param object       对象
     * @param method       方法
     * @return 调度任务 key
     */
    @NotNull
    public static String getTaskNameOrDefault(ScheduleTask scheduleTask,
                                               Object object,
                                               Method method) {
        return getTaskNameOrDefault(scheduleTask, object.getClass(), method);
    }


}
