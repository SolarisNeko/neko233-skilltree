package com.neko233.skilltree.aop.demo.ok;

import com.neko233.skilltree.aop.api.impl.demo.TestAop;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
@TestAop
public class OkHandlerImpl implements OkHandler {

    @Override
    public int count() {
        return 1;
    }

}
