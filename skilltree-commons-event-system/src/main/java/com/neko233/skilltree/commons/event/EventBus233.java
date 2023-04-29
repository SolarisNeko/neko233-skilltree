package com.neko233.skilltree.commons.event;

import com.neko233.skilltree.commons.core.annotation.Nullable;
import com.neko233.skilltree.commons.event.annotation.EventConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author SolarisNeko
 * Date on 2023-04-27
 */
public interface EventBus233<SUBSCRIBER> {


    /**
     * 注册订阅者
     *
     * @param eventName  事件名 / null 具体实现的规则来选择 eventName
     * @param subscriber 订阅者
     */
    void register(@Nullable String eventName, SUBSCRIBER subscriber, boolean isOnce);


    default void register(@Nullable String eventName, SUBSCRIBER subscriber) {
        register(eventName, subscriber, false);
    }

    default void register(SUBSCRIBER subscriber) {
        Objects.requireNonNull(subscriber);
        checkSubscriber(subscriber);

        register(null, subscriber, false);
    }


    /**
     * 只注册一次
     *
     * @param subscriber 订阅者
     */

    default void registerOnce(SUBSCRIBER subscriber) {
        Objects.requireNonNull(subscriber);
        checkSubscriber(subscriber);

        register(null, subscriber, true);
    }


    default void registerOnce(@Nullable String eventName, SUBSCRIBER subscriber) {
        register(eventName, subscriber, true);
    }


    /**
     * 解除注册订阅者
     *
     * @param subscriber 被解除的订阅者
     */
    void unregister(@Nullable String eventName, SUBSCRIBER subscriber);


    /**
     * 投递事件
     *
     * @param event 事件
     */
    void post(@Nullable String eventName, Object event);

    /**
     * 投递事件
     *
     * @param event 事件
     */
    default void post(Object event) {
        if (event == null) {
            return;
        }
        post(null, event);
    }


    // ------------------------- default --------------------


    static void checkSubscriber(Object subscriber) {
        if (subscriber instanceof Class) {
            throw new IllegalArgumentException("subscriber can not is a Class = " + ((Class<?>) subscriber).getName());
        }
    }


    /**
     * 获取声明事件 class
     *
     * @param target 目标类
     * @return 类
     */
    default List<Class<?>> getDeclaredEventClasses(Class<?> target) {
        List<Class<?>> eventClasses = new ArrayList<>();

        // interface part
        Class<?>[] declaredClasses = target.getDeclaredClasses();
        for (Class<?> declaredClass : declaredClasses) {
            if (declaredClass.isAnnotationPresent(EventConsumer.class)) {
                eventClasses.add(declaredClass);
            }
        }

        // this
        if (target.isAnnotationPresent(EventConsumer.class)) {
            eventClasses.add(target);
        }
        return eventClasses;
    }

    default String getRealEventName(@Nullable String eventName, Object event) {
        return eventName == null ? event.getClass().getName() : eventName;
    }


}
