package com.neko233.skilltree.commons.event.bus;

import com.neko233.skilltree.commons.event.annotation.EventConsumer;

/**
 * @author SolarisNeko
 * Date on 2023-04-28
 */
@EventConsumer(value = MyEvent.class)
public class MyEvent {

    void onMyEvent(MyEvent event) {
        // do something with event

    }


}