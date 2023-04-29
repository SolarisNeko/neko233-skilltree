package com.neko233.skilltree.aop.api.impl.demo;

import com.neko233.skilltree.aop.api.AopApi;

import java.lang.reflect.Method;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public class TestAopApi implements AopApi {


    @Override
    public void preHandle(Method method, Object target, Object[] args) {
        System.out.println(" TestAopApi preHandle");
    }

    @Override
    public boolean tryEatException(Exception e) {
        return true;
    }

    @Override
    public void postHandle(Method method, Object target, Object[] args) {
        System.out.println(" TestAopApi postHandle");

    }
}
