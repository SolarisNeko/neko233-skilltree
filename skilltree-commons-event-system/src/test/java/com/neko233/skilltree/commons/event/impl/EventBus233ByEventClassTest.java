package com.neko233.skilltree.commons.event.impl;

import com.neko233.skilltree.commons.event.EventBus233;
import com.neko233.skilltree.commons.event.EventBus233DefaultFactory;
import com.neko233.skilltree.commons.event.impl.demo.MyEvent;
import com.neko233.skilltree.commons.event.impl.demo.MyEventConsumer;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public class EventBus233ByEventClassTest {


    @Test
    public void test() {
        EventBus233<Object> eventBus233 = EventBus233DefaultFactory.EVENT_CLASS;

        MyEventConsumer consumer = new MyEventConsumer();

        MyEvent event = new MyEvent();

        eventBus233.register(consumer);

        eventBus233.post(event);
        eventBus233.post(event);
        eventBus233.post(event);
    }


}