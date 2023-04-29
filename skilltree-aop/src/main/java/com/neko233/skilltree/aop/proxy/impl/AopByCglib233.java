package com.neko233.skilltree.aop.proxy.impl;

import com.neko233.skilltree.aop.AopAnnotationRegistry233;
import com.neko233.skilltree.aop.api.AopApi;
import com.neko233.skilltree.aop.proxy.Aop;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.util.function.Supplier;

/**
 * @author SolarisNeko on 2022-01-01
 */
@Slf4j
public class AopByCglib233 implements Aop {

    public static final AopByCglib233 singleton = new AopByCglib233();

    @Override
    public <T> T proxyByApi(T target, AopApi aopApi) {
        return generateProxy(target, aopApi);
    }

    private static <T> T generateProxy(T target, AopApi aopApi) {

        aopApi.init();

        // 创建代理对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());

        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            aopApi.preHandle(method, target, args);

            Object returnValue = null;
            int retryCount = Math.max(0, aopApi.retryCountOnError());
            for (int tryCount = 0; tryCount < 1 + retryCount; tryCount++) {
                try {
                    returnValue = proxy.invokeSuper(obj, args);
                    break;
                } catch (Exception e) {
                    boolean isEat = aopApi.tryEatException(e);
                    if (isEat) {
                        continue;
                    }
                    throw e;
                }
            }

            aopApi.postHandle(method, target, args);
            return returnValue;
        });

        aopApi.finish();

        return (T) enhancer.create();
    }

    @Override
    public <T> T proxyByAnnotation(T target) {
        if (target == null) {
            return null;
        }
        // 定义一个类加载器
        Class<?> clazz = target.getClass();

        T proxyTarget = target;
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annoType = annotation.annotationType();
            Supplier<AopApi> aopApi = AopAnnotationRegistry233.singleton.get(annoType);
            if (aopApi == null) {
                continue;
            }
            AopApi targetAopApi = aopApi.get();
            if (targetAopApi == null) {
                continue;
            }
            proxyTarget = generateProxy(proxyTarget, targetAopApi);
            break;
        }


        return proxyTarget;
    }
}