package com.neko233.skilltree.commons.event.impl;

import com.neko233.skilltree.commons.event.EventBus233;
import com.neko233.skilltree.commons.event.EventBus233DefaultFactory;
import com.neko233.skilltree.commons.event.delegate.EventListener;
import com.neko233.skilltree.commons.event.impl.demo.MyAsyncEventListener;
import com.neko233.skilltree.commons.event.impl.demo.MyEvent;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public class EventBus233ByAsyncTest {

    @Test
    public void post() throws InterruptedException {


        EventBus233<EventListener<?>> eventBus233 = EventBus233DefaultFactory.ASYNC;

        EventListener<MyEvent> consumer = new MyAsyncEventListener();

        MyEvent event = new MyEvent();

        eventBus233.register(consumer);

        eventBus233.post(event);
        eventBus233.post(event);
        eventBus233.post(event);

        TimeUnit.MILLISECONDS.sleep(300);

    }
}