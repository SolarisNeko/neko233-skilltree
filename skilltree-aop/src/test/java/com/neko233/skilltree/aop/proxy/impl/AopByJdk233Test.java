package com.neko233.skilltree.aop.proxy.impl;

import com.neko233.skilltree.aop.demo.err.EatExceptionAopApi;
import com.neko233.skilltree.aop.demo.err.FailOneThenSuccessHandler;
import com.neko233.skilltree.aop.demo.err.FailOneThenSuccessHandlerImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-04-27
 */
public class AopByJdk233Test {


    @Test
    public void baseTest() {
        FailOneThenSuccessHandler failOneThenSuccessHandler = new FailOneThenSuccessHandlerImpl();

        EatExceptionAopApi aopApi = new EatExceptionAopApi();
        FailOneThenSuccessHandler handler = AopByJdk233.singleton.proxyByApi(failOneThenSuccessHandler, aopApi);

        int handle = 0;
        try {
            handle = handler.getHandleCount();
        } catch (Exception e) {
        }

        Assert.assertEquals(1, handle);
    }




}