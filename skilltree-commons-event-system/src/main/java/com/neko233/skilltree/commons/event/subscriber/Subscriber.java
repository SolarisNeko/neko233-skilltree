package com.neko233.skilltree.commons.event.subscriber;

import com.neko233.skilltree.commons.core.base.ObjectUtils233;
import com.neko233.skilltree.commons.core.base.StringUtils233;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

public class Subscriber {

    // 事件名
    private final String eventName;
    // 调用对象
    private final Object subscriber;
    private final Class<?> eventClass;
    private Method lazyCallbackMethod;
    private Class<?> firstParameterType;

    private String consumerClassName;
    private String methodName;


    public Subscriber(String eventName, Object subscriber, Class<?> eventClass) {
        this.subscriber = subscriber;
        this.eventName = eventName;
        this.eventClass = eventClass;
        this.consumerClassName = subscriber.getClass().getName();

        Class<?> targetClass = subscriber instanceof Class ? (Class<?>) subscriber : subscriber.getClass();
        // method
        this.methodName = "on" + eventClass.getSimpleName();
        // method(Class eventClass)
        Method declaredMethod = null;
        try {
            declaredMethod = targetClass.getDeclaredMethod(this.methodName, eventClass);
        } catch (NoSuchMethodException e) {
            String format = StringUtils233.format("class = {}, no such method name = {}",
                    targetClass.getName(), this.methodName);
            throw new RuntimeException(format, e);
        }
        declaredMethod.setAccessible(true);
        this.lazyCallbackMethod = declaredMethod;
        if (Objects.isNull(this.lazyCallbackMethod)) {
            String format = StringUtils233.format("class = {}, no such method name = {}",
                    targetClass.getName(), this.methodName);
            throw new RuntimeException(format);
        }

        Class<?>[] parameterTypes = this.lazyCallbackMethod.getParameterTypes();
        if (parameterTypes.length != 1) {
            String format = StringUtils233.format("class = {}, method name = {}, parameter type is not = {}, please check",
                    targetClass.getName(), this.methodName, eventClass.getName());
            throw new RuntimeException(format);
        }
        this.firstParameterType = parameterTypes[0];
        if (ObjectUtils233.isNotEquals(firstParameterType.getName(), eventClass.getName())) {
            String format = StringUtils233.format("class = {}, method name = {}, parameter type is not = {}, please check",
                    targetClass.getName(), this.methodName, eventClass.getName());
            throw new RuntimeException(format);
        }
    }

    /**
     * 获取订阅对象
     */
    public Object getSubscriber() {
        return subscriber;
    }


    /**
     * 调用
     *
     * @param event 事件
     */
    public void call(Object event) throws Exception {
        if (isNotAccept(event)) {
            return;
        }
        try {
            if (lazyCallbackMethod != null) {
                handle(event);
                return;
            }

            lazyInitHandler(event);

            handle(event);
        } catch (Exception e) {
            String format = StringUtils233.format("[Subscriber] Error calling class = {}, methodName = {}",
                    this.consumerClassName, consumerClassName);
            throw new RuntimeException(format, e);
        }
    }

    private void lazyInitHandler(Object event) throws NoSuchMethodException {

    }

    private void handle(Object event) throws IllegalAccessException, InvocationTargetException {
        lazyCallbackMethod.invoke(subscriber, event);
    }

    public boolean isAccept(Object event) {
        Class<?> eventClass = Optional.ofNullable(event).map(Object::getClass).orElse(null);
        return this.firstParameterType == eventClass;
    }

    public boolean isNotAccept(Object event) {
        return !isAccept(event);
    }
}