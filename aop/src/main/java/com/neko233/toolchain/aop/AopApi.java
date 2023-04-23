package com.neko233.toolchain.aop;

import java.lang.reflect.Method;

/**
 * {@link AopApi} recommend just a functional class, just action, no state, and singleton.
 * 建议只有行为, 无状态, 且 singleton
 *
 * @author SolarisNeko on 2023-02-16
 **/
public interface AopApi {

    // --------------- global 全剧相关 --------------

    void init();

    void destroy();

    // --------------- method invoke --------------

    /**
     * 前处理
     *
     * @param method 方法
     * @param target 调用对象
     * @param args   参数
     */
    default void preHandle(Method method, Object target, Object[] args) {
    }


    default int retryCountOnError() {
        return 0;
    }

    /**
     * 当某些异常时, 自动重试
     *
     * @param e 发生的异常
     */
    boolean tryEatException(Exception e);

    /**
     * 后处理
     *
     * @param method 方法
     * @param target 调用对象
     * @param args   参数
     */
    default void postHandle(Method method, Object target, Object[] args) {
    }


}
