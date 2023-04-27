package com.neko233.skilltree.commons.event.subscriber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Subscriber {

    private final Object subscriber;
    private final Class<?> eventClass;
    private Method lazyCallbackMethod;


    public Subscriber(Object subscriber, Class<?> eventClass) {
        this.subscriber = subscriber;
        this.eventClass = eventClass;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    /**
     * 调
     *
     * @param event
     */
    public void call(Object event) {
        try {
            if (lazyCallbackMethod != null) {
                handleCallback(event);
                return;
            }
            Class<?> targetClass = subscriber.getClass();
            this.lazyCallbackMethod = targetClass.getDeclaredMethod("on" + eventClass.getSimpleName(), eventClass);

            handleCallback(event);
        } catch (Exception e) {
            throw new RuntimeException("Error calling subscriber method", e);
        }
    }

    private void handleCallback(Object event) throws IllegalAccessException, InvocationTargetException {
        lazyCallbackMethod.invoke(subscriber, event);
    }

}