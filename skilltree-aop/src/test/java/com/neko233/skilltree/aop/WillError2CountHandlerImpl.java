package com.neko233.skilltree.aop;

/**
 * @author SolarisNeko on 2023-02-16
 **/
public class WillError2CountHandlerImpl implements WillError2CountHandler {

    int count = 0;

    public int handle() {
        if (count < 2) {
            count++;
            throw new RuntimeException("mock exception");
        }
        return 1;
    }

}
