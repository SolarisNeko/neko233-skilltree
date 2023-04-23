package com.neko233.toolchain.commons.core.base;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadUtils233 {

    public static Thread newThread(String threadName, Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(threadName);
        return thread;
    }

    /**
     * Sleeps ? ms
     *
     * @param ms How long to sleep (millis seconds)
     */
    public static void sleep(long ms) {
        try {
            log.debug("Sleeping for {} ms", ms);
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.error("Interrupted while sleeping for {} ms: {}", ms, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
