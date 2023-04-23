package com.neko233.toolchain.aop;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class AopAnnotationFactory233 {

    public static final AopAnnotationFactory233 singleton = new AopAnnotationFactory233();

    private AopAnnotationFactory233() {

    }

    private final Map<Annotation, Supplier<AopApi>> ANNOTATION_AOP_API_MAP = new ConcurrentHashMap<>();

    public Supplier<AopApi> get(Annotation annotation) {
        return ANNOTATION_AOP_API_MAP.get(annotation);
    }

    public Supplier<AopApi> register(Annotation annotation, Supplier<AopApi> supplier) {
        return ANNOTATION_AOP_API_MAP.put(annotation, supplier);
    }

}
