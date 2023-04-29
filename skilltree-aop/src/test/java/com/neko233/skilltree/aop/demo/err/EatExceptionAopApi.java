package com.neko233.skilltree.aop.demo.err;

import com.neko233.skilltree.aop.api.AopApi;

import java.lang.reflect.Method;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public class EatExceptionAopApi implements AopApi {

    @Override
    public void init() {
        System.out.println("init");
    }

    @Override
    public void preHandle(Method method, Object target, Object[] args) {
        System.out.println("pre");
    }

    @Override
    public int retryCountOnError() {
        return 3;
    }

    @Override
    public boolean tryEatException(Exception e) {
        return true;
    }

    @Override
    public void postHandle(Method method, Object target, Object[] args) {
        System.out.println("post");
    }


    @Override
    public void finish() {
        System.out.println("destroy");
    }

}
