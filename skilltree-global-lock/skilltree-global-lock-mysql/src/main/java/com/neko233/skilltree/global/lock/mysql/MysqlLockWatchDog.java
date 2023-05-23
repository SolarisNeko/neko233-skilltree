package com.neko233.skilltree.global.lock.mysql;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Lock 看门狗
 *
 * @author SolarisNeko on 2023-05-01
 */
@Slf4j
public class MysqlLockWatchDog {

    private final AtomicBoolean isStop = new AtomicBoolean();
    private final long watchTime;
    private final Runnable target;
    private final Thread thread;

    public MysqlLockWatchDog(Runnable target,
                             long watchTime,
                             TimeUnit timeUnit) {
        this.watchTime = timeUnit.toMillis(watchTime);
        this.target = target;

        this.thread = new Thread(() -> {
            while (!isStop.get()) {
                try {
                    Thread.sleep(this.watchTime);
                } catch (InterruptedException e) {
                }
                this.target.run();
            }
        }, "lock-watch-dog");
    }

    public void start() {
        if (!isStop.get()) {
            return;
        }
        thread.start();
    }

    public void stop() {
        this.isStop.set(true);
        ;
    }
}
