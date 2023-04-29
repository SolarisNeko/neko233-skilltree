package com.neko233.skilltree.commons.event.impl.demo;

import com.neko233.skilltree.commons.event.delegate.EventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
@Slf4j
public class MyAsyncEventListener implements EventListener<MyEvent> {

    @Override
    public void handle(MyEvent event) {

        log.info("async ok ");
    }

}
