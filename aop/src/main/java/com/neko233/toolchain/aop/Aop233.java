package com.neko233.toolchain.aop;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

/**
 * @author SolarisNeko on 2022-01-01
 */
@Slf4j
public class Aop233 {

    public static <T> T proxy(T target, AopApi aopApi) {
        if (target == null) {
            return null;
        }
        // 定义一个类加载器
        ClassLoader loader = target.getClass().getClassLoader();
        // 定义一个接口数组
        Class<?>[] interfaces = target.getClass().getInterfaces();

        // 定义一个 InvocationHandler
        InvocationHandler handler = (proxy, method, args) -> {
            aopApi.preHandle(method, target, args);
            Object returnValue = null;
            int retryCount = Math.max(0, aopApi.retryCountOnError());
            for (int tryCount = 0; tryCount < 1 + retryCount; tryCount++) {
                try {
                    returnValue = method.invoke(target, args);
                    break;
                } catch (Exception e) {
                    boolean isEat = aopApi.tryEatException(e);
                    if (isEat) {
                        continue;
                    }
                    throw e.getCause();
                }
            }
            aopApi.postHandle(method, target, args);
            return returnValue;
        };

        return (T) Proxy.newProxyInstance(loader, interfaces, handler);
    }

    public static <T> T proxyByAnnotation(T target) {
        if (target == null) {
            return null;
        }
        // 定义一个类加载器
        Class<?> clazz = target.getClass();
        ClassLoader loader = clazz.getClassLoader();
        // 定义一个接口数组
        Class<?>[] interfaces = clazz.getInterfaces();


        Annotation[] annotations = clazz.getAnnotations();
        AopApi targetAopApi = null;
        for (Annotation annotation : annotations) {
            Supplier<AopApi> aopApi = AopAnnotationFactory233.singleton.get(annotation);
            if (aopApi == null) {
                continue;
            }
            targetAopApi = aopApi.get();
        }

        if (targetAopApi == null) {
            return target;
        }

        // 定义一个 InvocationHandler
        AopApi finalTargetAopApi = targetAopApi;
        InvocationHandler handler = (proxy, method, args) -> {
            finalTargetAopApi.preHandle(method, target, args);
            Object returnValue = null;
            int retryCount = Math.max(0, finalTargetAopApi.retryCountOnError());
            for (int tryCount = 0; tryCount < 1 + retryCount; tryCount++) {
                try {
                    returnValue = method.invoke(target, args);
                    break;
                } catch (Exception e) {
                    boolean isEat = finalTargetAopApi.tryEatException(e);
                    if (isEat) {
                        continue;
                    }
                    throw e.getCause();
                }
            }
            finalTargetAopApi.postHandle(method, target, args);
            return returnValue;
        };
        T proxyObj = (T) Proxy.newProxyInstance(loader, interfaces, handler);
        return proxyObj;
    }
}