package com.neko233.toolchain.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SolarisNeko on 2023-02-14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class WorkerTask {

    private String type;
    private Runnable task;
    private boolean isCanDrop;
    private long addMs;

    public static WorkerTask create(String type, Runnable task) {
        return WorkerTask.builder()
                .type(type)
                .task(task)
                .isCanDrop(false)
                .addMs(System.currentTimeMillis())
                .build();
    }

    public static WorkerTask shutdown() {
        return create("shutdown", () -> {
            log.info("shutdown workerTask, thread name = {}", Thread.currentThread().getName());
        });
    }


    public String logString() {
        return "WorkerTask{" +
                "type='" + type + '\'' +
                ", isCanDrop=" + isCanDrop +
                ", addMs=" + addMs +
                '}';
    }
}
