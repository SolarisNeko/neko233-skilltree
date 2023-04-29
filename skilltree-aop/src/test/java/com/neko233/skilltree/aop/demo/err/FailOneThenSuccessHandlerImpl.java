package com.neko233.skilltree.aop.demo.err;

import com.neko233.skilltree.aop.api.impl.demo.TestAop;

/**
 * @author SolarisNeko on 2023-02-16
 **/
@TestAop
public class FailOneThenSuccessHandlerImpl implements FailOneThenSuccessHandler {

    int count = 0;

    @Override
    public int getHandleCount() {
        if (count < 2) {
            // [0, 2) -> 2
            count++;
            throw new RuntimeException("mock exception");
        }
        // 1
        return 1;
    }

}
