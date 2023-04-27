package com.neko233.skilltree.commons.core.threadPool;

import java.util.concurrent.*;

public class ThreadPoolBuilder {

    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    private TimeUnit keepAliveTimeUnit;
    private BlockingQueue<Runnable> taskQueue;
    private ThreadFactory threadFactory;
    private RejectedExecutionHandler rejectedExecutionHandler;

    public static ThreadPoolBuilder builder() {
        return new ThreadPoolBuilder();
    }

    public ThreadPoolExecutor build() {
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                keepAliveTimeUnit,
                taskQueue,
                threadFactory,
                rejectedExecutionHandler
        );
    }

    public ThreadPoolBuilder corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public ThreadPoolBuilder maximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
        return this;
    }

    public ThreadPoolBuilder keepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public ThreadPoolBuilder keepAliveTimeUnit(TimeUnit keepAliveTimeUnit) {
        this.keepAliveTimeUnit = keepAliveTimeUnit;
        return this;
    }

    public ThreadPoolBuilder taskQueue(BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
        return this;
    }


    public ThreadPoolBuilder threadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }


    public ThreadPoolBuilder rejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
        return this;
    }


}
