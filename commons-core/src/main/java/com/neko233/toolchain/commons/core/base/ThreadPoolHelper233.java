package com.neko233.toolchain.commons.core.base;

import com.neko233.toolchain.commons.core.threadPool.ThreadFactoryByPrefixName;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2023-01-28
 */
public class ThreadPoolHelper233 {

    public static ThreadPoolExecutor ioThreadPool(String name, final RejectedExecutionHandler rejectedExecutionHandler) {
        return new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() * 2,
                Runtime.getRuntime().availableProcessors() * 2,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryByPrefixName("io-tp-" + name),
                ObjectUtils233.getOrDefault(rejectedExecutionHandler, new ThreadPoolExecutor.AbortPolicy())
        );
    }

    public static ThreadPoolExecutor calculateThreadPool(String name, RejectedExecutionHandler rejectedExecutionHandler) {
        return new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryByPrefixName("calc-tp-" + name),
                ObjectUtils233.getOrDefault(rejectedExecutionHandler, new ThreadPoolExecutor.AbortPolicy())
        );
    }


}
