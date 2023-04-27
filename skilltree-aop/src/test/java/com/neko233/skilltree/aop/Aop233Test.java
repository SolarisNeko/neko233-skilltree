package com.neko233.skilltree.aop;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author SolarisNeko
 * Date on 2023-04-27
 */
public class Aop233Test {


    @Test
    public void baseTest() {
        WillError2CountHandler willError2CountHandler = new WillError2CountHandlerImpl();

        WillError2CountHandler handler = Aop233.proxy(willError2CountHandler, EatExceptionAopApi);

        int handle = 0;
        try {
            handle = handler.handle();
        } catch (Exception e) {
        }

        Assert.assertEquals(1, handle);
    }


    public static final AopApi EatExceptionAopApi = new AopApi() {
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
        public void destroy() {
            System.out.println("destroy");
        }

    };


}