package com.neko233.skilltree.commons.event.delegate;

import com.neko233.skilltree.commons.core.base.ArrayUtils233;
import com.neko233.skilltree.commons.core.base.ObjectTypeUtils233;

import java.lang.reflect.Type;

/**
 * Delegate 委托模式 = 我提供 data -> EventObserverManager -> 遍历 Observer 进行链式调用
 * Observer 只需要实现下面这个 interface API 即可加入到 EventObserverManager 中, 等待 notify
 *
 * @author SolarisNeko
 * Date on 2022-10-30
 */
public interface EventListener<T> {

    /**
     * @param event 任意数据
     */
    void handle(T event);

    default String getEventClassName() {
        Class<?> clazz = ObjectTypeUtils233.getGenericClassNameByHumanIndex(this, 1);
        if (clazz == null) {
            return "null";
        }
        return clazz.getName();
    }

}
