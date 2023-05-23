package com.neko233.skilltree.global.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author SolarisNeko on  2020-01-01
 * @version V1.0
 */
public interface GlobalLock extends Lock {


    default void lock() {
        throw new RuntimeException("不支持的操作");
    }


    default void lockInterruptibly() throws RuntimeException {
        throw new RuntimeException("不支持的操作");
    }


    default boolean tryLock() {
        throw new RuntimeException("不支持的操作");
    }


    default boolean tryLock(long time,
                            TimeUnit unit) throws RuntimeException {
        throw new RuntimeException("不支持的操作");
    }


    default void unlock() {
        throw new RuntimeException("不支持的操作");
    }


    default Condition newCondition() {
        throw new RuntimeException("不支持的操作");
    }

}
