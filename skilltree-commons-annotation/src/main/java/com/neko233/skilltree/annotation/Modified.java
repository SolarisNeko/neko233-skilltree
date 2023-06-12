package com.neko233.skilltree.annotation;

import java.lang.annotation.*;

/**
 * 可修改的
 *
 * @author SolarisNeko
 * Date on 2023-01-28
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Modified {


}
