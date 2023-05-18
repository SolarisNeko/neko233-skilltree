package com.neko233.skilltree.ioc.annotation;


import com.neko233.skilltree.ioc.constant.ProvideType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提供给容器管理
 *
 * @author SolarisNeko on 2023-05-01
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Provider {

    // 默认单例
    ProvideType type() default ProvideType.SINGLETON;

}
