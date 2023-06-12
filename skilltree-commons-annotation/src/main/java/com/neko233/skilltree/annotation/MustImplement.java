package com.neko233.skilltree.annotation;

import java.lang.annotation.*;

/**
 * 必须是实现的 class
 *
 * @author SolarisNeko
 * Date on 2023-01-28
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MustImplement {


}
