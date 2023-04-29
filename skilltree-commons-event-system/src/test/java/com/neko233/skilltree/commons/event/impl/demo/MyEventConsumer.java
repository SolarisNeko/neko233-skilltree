package com.neko233.skilltree.commons.event.impl.demo;

import com.neko233.skilltree.commons.event.annotation.EventConsumer;

/**
 * @author SolarisNeko
 * Date on 2023-04-28
 */
@EventConsumer({MyEvent.class})
public class MyEventConsumer {

    int count = 0;

    public void onMyEvent(MyEvent event) {
        if (count < 1) {
            count++;
            throw new IllegalArgumentException("test");
        }
        System.out.println("hal");
    }


}