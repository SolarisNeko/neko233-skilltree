package com.neko233.skilltree.scheduler.client;

import com.neko233.skilltree.scheduler.client.annotation.ScheduleTaskParamText;
import com.neko233.skilltree.scheduler.client.dto.TaskStubInvokeResult;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调用任务
 *
 * @author SolarisNeko on 2023-05-01
 */
@Slf4j
@Getter
public class TaskStubRegistry {

    private final Map<String, Object> taskNameToObjectMap = new ConcurrentHashMap<>();
    private final Map<String, Method> taskNameToMethodMap = new ConcurrentHashMap<>();


    public void register(String taskName,
                         Object object,
                         Method method) {
        taskNameToObjectMap.put(taskName, object);
        taskNameToMethodMap.put(taskName, method);
    }

    public void unregister(String taskName) {
        taskNameToObjectMap.remove(taskName);
        taskNameToMethodMap.remove(taskName);
    }

    public TaskStubInvokeResult invokeTask(String taskName,
                                           @ScheduleTaskParamText String paramText) {
        Object obj = taskNameToObjectMap.get(taskName);
        Method method = taskNameToMethodMap.remove(taskName);

        TaskStubInvokeResult result = TaskStubInvokeResult.create();
        try {
            method.setAccessible(true);


            // Get the parameters of the method
            Parameter[] parameterArray = method.getParameters();

            // Iterate through the parameters
            Object[] paramContextArray = new Object[parameterArray.length];
            for (int i = 0; i < parameterArray.length; i++) {
                Parameter parameter = parameterArray[i];
                ScheduleTaskParamText annotation = parameter.getAnnotation(ScheduleTaskParamText.class);
                if (annotation == null) {
                    paramContextArray[i] = null;
                    continue;
                }
                paramContextArray[i] = paramText;
            }

            Object returnValue = method.invoke(obj, paramContextArray);

            if (returnValue == null) {
                return result.success("");
            }
            return result.success(String.valueOf(returnValue));
        } catch (Exception e) {
            log.error("[TaskStubRegistry] 调用执行失败, taskName = {}", taskName, e);
            return result.failure("");
        }


    }

}
