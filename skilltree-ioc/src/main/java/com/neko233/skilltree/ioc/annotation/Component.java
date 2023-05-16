package com.neko233.skilltree.ioc.annotation;


import com.neko233.skilltree.ioc.constant.ComponentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LuoHaoJun on 2023-05-16
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    // 默认单例
    ComponentType componentType() default ComponentType.SINGLETON;

}
