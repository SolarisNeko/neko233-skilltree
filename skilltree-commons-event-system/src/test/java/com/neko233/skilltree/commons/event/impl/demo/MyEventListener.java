package com.neko233.skilltree.commons.event.impl.demo;

import com.neko233.skilltree.commons.event.delegate.EventListener;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public class MyEventListener implements EventListener<MyEvent> {

    @Override
    public void handle(MyEvent event) {

        System.out.println("event ok");
    }

}
