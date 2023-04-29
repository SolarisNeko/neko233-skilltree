package com.neko233.skilltree.commons.event.impl;

import com.neko233.skilltree.commons.event.EventBus233;
import com.neko233.skilltree.commons.event.EventBus233DefaultFactory;
import com.neko233.skilltree.commons.event.delegate.EventListener;
import com.neko233.skilltree.commons.event.impl.demo.MyEvent;
import com.neko233.skilltree.commons.event.impl.demo.MyEventListener;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public class EventBus233BySyncTest {

    @Test
    public void post() {

        EventBus233<EventListener<?>> eventBus233 = EventBus233DefaultFactory.SYNC;

        EventListener<MyEvent> consumer = new MyEventListener();

        MyEvent event = new MyEvent();

        eventBus233.register(consumer);

        eventBus233.post(event);
        eventBus233.post(event);
        eventBus233.post(event);
    }
}