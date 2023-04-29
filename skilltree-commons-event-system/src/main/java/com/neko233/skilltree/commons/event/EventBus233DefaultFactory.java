package com.neko233.skilltree.commons.event;

import com.neko233.skilltree.commons.event.delegate.EventListener;
import com.neko233.skilltree.commons.event.impl.EventBus233ByAsync;
import com.neko233.skilltree.commons.event.impl.EventBus233ByEventClass;
import com.neko233.skilltree.commons.event.impl.EventBus233BySync;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public interface EventBus233DefaultFactory {

    EventBus233<Object> EVENT_CLASS = new EventBus233ByEventClass();

    EventBus233<EventListener<?>> SYNC = new EventBus233BySync();

    EventBus233<EventListener<?>> ASYNC = new EventBus233ByAsync();



}
