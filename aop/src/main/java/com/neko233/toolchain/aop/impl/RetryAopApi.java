package com.neko233.toolchain.aop.impl;

import com.neko233.toolchain.aop.AopApi;

import java.io.IOException;
import java.lang.reflect.Method;

public class RetryAopApi implements AopApi {

    private final int retryCount;

    public RetryAopApi(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void preHandle(Method method, Object target, Object[] args) {
        // Do nothing
    }

    @Override
    public void postHandle(Method method, Object target, Object[] args) {
        // Do nothing
    }

    @Override
    public boolean tryEatException(Exception e) {
        if (e instanceof IOException) {
            return true;
        }
        return false;
    }

    @Override
    public int retryCountOnError() {
        return retryCount;
    }

}
