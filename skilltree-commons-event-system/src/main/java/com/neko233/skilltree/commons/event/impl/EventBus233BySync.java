package com.neko233.skilltree.commons.event.impl;

import com.neko233.skilltree.commons.event.EventBus233;
import com.neko233.skilltree.commons.event.delegate.EventDispatcher;
import com.neko233.skilltree.commons.event.delegate.EventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Event Class 同时作为 subscriber, 调用其内部的 onClassName
 *
 * @author SolarisNeko
 */
@Slf4j
public class EventBus233BySync implements EventBus233<EventListener<?>> {

    EventDispatcher eventDispatcher = EventDispatcher.createDefault();

    @Override
    public void register(String eventName, EventListener<?> o, boolean isOnce) {
        eventDispatcher.register(eventName, o);
    }

    @Override
    public void unregister(String eventName, EventListener<?> o) {
        eventDispatcher.unregister(eventName, o);
    }


    @Override
    public void post(String eventName, Object event) {
        String realEventName = getRealEventName(eventName, event);
        eventDispatcher.sync(realEventName, event);
    }
}


