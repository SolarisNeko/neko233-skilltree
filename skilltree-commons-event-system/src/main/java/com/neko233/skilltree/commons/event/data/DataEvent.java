package com.neko233.skilltree.commons.event.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 事件包装类
 *
 * @author SolarisNeko
 * Date on 2023-04-29
 */
@Data
@AllArgsConstructor
@Slf4j
public class DataEvent {

    private String eventName;
    private Object data;


    public void clear() {
        this.eventName = null;
        this.data = null;
    }

    public <T> T getData() {
        try {
            if (data == null) {
                return null;
            }
            return (T) data;
        } catch (Exception e) {
            log.error("[Event] cast type to T error", e);
            return null;
        }
    }

}
