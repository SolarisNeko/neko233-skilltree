package com.neko233.toolchain.commons.event.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventConsumer {

    Class<?> value();

}
