package com.neko233.skilltree.aop;

import com.neko233.skilltree.aop.api.AopApi;
import com.neko233.skilltree.aop.api.impl.demo.TestAop;
import com.neko233.skilltree.aop.api.impl.demo.TestAopApi;
import com.neko233.skilltree.aop.api.impl.retry.RetryAop;
import com.neko233.skilltree.aop.api.impl.retry.RetryAopApi;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class AopAnnotationRegistry233 {

    public static final AopAnnotationRegistry233 singleton = new AopAnnotationRegistry233();


    private final Map<Class<? extends Annotation>, Supplier<AopApi>> ANNOTATION_AOP_API_MAP
            = new ConcurrentHashMap<>();

    private AopAnnotationRegistry233() {
        register(RetryAop.class, () -> new RetryAopApi(3));
        register(TestAop.class, TestAopApi::new);
    }


    /**
     * 获取给定注解类型对应的 AOP 实现的工厂函数。
     *
     * @param annotation 注解
     * @return 生成器
     */
    public Supplier<AopApi> get(Class<? extends Annotation> annotation) {
        return ANNOTATION_AOP_API_MAP.get(annotation);
    }

    /**
     * 注册
     *
     * @param annotationClass 注解 class
     * @param supplier        供应器
     * @return
     */
    public AopAnnotationRegistry233 register(Class<? extends Annotation> annotationClass, Supplier<AopApi> supplier) {
        ANNOTATION_AOP_API_MAP.put(annotationClass, supplier);
        return this;
    }

}
