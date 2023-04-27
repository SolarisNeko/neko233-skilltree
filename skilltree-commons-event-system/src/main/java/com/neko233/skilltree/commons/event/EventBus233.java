package com.neko233.skilltree.commons.event;

import com.neko233.skilltree.commons.event.annotation.EventConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2023-04-27
 */
public interface EventBus233 {


    /**
     * 注册订阅者
     *
     * @param subscriber 订阅者
     */
    void register(Object subscriber);

    /**
     * 解除注册订阅者
     *
     * @param subscriber 被解除的订阅者
     */
    void unregister(Object subscriber);

    /**
     * 投递事件
     *
     * @param event 事件
     */
    void post(Object event);


    /**
     * 获取声明事件 class
     *
     * @param target 目标类
     * @return 类
     */
    default List<Class<?>> getDeclaredEventClasses(Class<?> target) {
        List<Class<?>> eventClasses = new ArrayList<>();
        for (Class<?> declaredClass : target.getDeclaredClasses()) {
            if (declaredClass.isAnnotationPresent(EventConsumer.class)) {
                eventClasses.add(declaredClass);
            }
        }
        return eventClasses;
    }
}
