package com.neko233.skilltree.commons.event.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventConsumer {

    Class<?> value();

}
