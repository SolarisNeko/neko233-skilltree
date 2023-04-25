package com.neko233.toolchain.actor;


import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * worker 一直 running 运行某个功能
 */
@Slf4j
@Getter
@ToString
public class Worker implements Runnable {

    private static final WorkerTask STOP_SIGNAL = WorkerTask.shutdown();
    private final String workerType;
    private final int workerIndex;
    private final Thread thread;
    private volatile boolean isRunning;
    private volatile boolean forceStop;
    private final BlockingQueue<WorkerTask> taskQueue;


    public Worker(String workerType, int workerIndex, int queueLength) {
        this.workerType = workerType;
        this.workerIndex = workerIndex;
        String name = String.format("%s-%d", workerType, workerIndex);
        this.thread = new Thread(this, name);
        this.thread.setDaemon(false);
        this.taskQueue = new ArrayBlockingQueue<>(queueLength);
    }

    public void start() throws Throwable {
        this.isRunning = true;
        this.forceStop = false;
        this.thread.start();
    }

    public void sendStopSignal() {
        this.isRunning = false;

        for (int times = 0; times < 1000; times++) {
            if (this.taskQueue.offer(STOP_SIGNAL)) {
                return;
            }
        }

        log.error("[Worker] add stop signal failed. threadName={} taskQueueSize={}", thread.getName(), taskQueue.size());
    }

    public void waitShutdown(long globalStopMs, long awaitInterruptionTimeoutMS) {
        this.sendStopSignal();
        // 先等 waitTimeMS, 等其安全退出

        try {
            if (awaitInterruptionTimeoutMS <= 0) {
                this.thread.join();
            } else {
                long finishStopSpendMs = System.currentTimeMillis() - globalStopMs;
                long remainWaitTimeMS = awaitInterruptionTimeoutMS - finishStopSpendMs;
                if (remainWaitTimeMS > 0) {
                    this.thread.join(remainWaitTimeMS);
                }
            }
        } catch (InterruptedException e) {
        }

        // 如果线程还存活，则直接发起中断
        if (this.thread.isAlive()) {
            long finishStopSpendMs = System.currentTimeMillis() - globalStopMs;
            log.warn("[Worker] thread is still alive then we will interrupt it. threadName={} taskQueueSize={} escapedTimeMS={}",
                    thread.getName(), taskQueue.size(), finishStopSpendMs);
            this.forceStop = true;
            this.thread.interrupt();
            try {
                this.thread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    public void addTask(WorkerTask workerTask) {
        boolean isOk = taskQueue.offer(workerTask);
        if (!isOk) {
            log.error("[Worker] task queue is full! Task:{}", workerTask.getType());
        }
    }

    @Override
    public void run() {
        WorkerTask workerTask;
        while (isRunning || !taskQueue.isEmpty()) {
            try {
                // 由于 offer shutdown signal 可能会失败
                workerTask = isRunning ? taskQueue.take() : taskQueue.poll();
                if (workerTask == null || workerTask == STOP_SIGNAL) {
                    continue;
                }
                Runnable runnable = workerTask.getTask();
                if (runnable == null) {
                    log.error("why workerTask type = {} runnable is null ?", workerTask.getType());
                    continue;
                }
                runnable.run();

            } catch (InterruptedException e) {
                if (isRunning) {// 减少日志污染
                    log.error("[Worker] do task error!", e);
                }
            } catch (Throwable t) {
                log.error("[Worker] do task error!", t);
            }
        }
    }


}
