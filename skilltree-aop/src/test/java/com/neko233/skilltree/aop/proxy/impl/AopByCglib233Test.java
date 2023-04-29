package com.neko233.skilltree.aop.proxy.impl;

import com.neko233.skilltree.aop.AopFactory233;
import com.neko233.skilltree.aop.demo.err.EatExceptionAopApi;
import com.neko233.skilltree.aop.demo.err.FailOneThenSuccessHandler;
import com.neko233.skilltree.aop.demo.err.FailOneThenSuccessHandlerImpl;
import com.neko233.skilltree.aop.demo.ok.OkHandler;
import com.neko233.skilltree.aop.demo.ok.OkHandlerImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public class AopByCglib233Test {

    @Test
    public void proxyByApi() {
        FailOneThenSuccessHandler failOneThenSuccessHandler = new FailOneThenSuccessHandlerImpl();

        EatExceptionAopApi aopApi = new EatExceptionAopApi();

        FailOneThenSuccessHandler handler = AopFactory233.CGLIB.proxyByApi(failOneThenSuccessHandler, aopApi);

        int executeCount = 0;
        try {
            executeCount = handler.getHandleCount();
        } catch (Exception e) {
            System.err.println(e);
        }

        Assert.assertEquals(1, executeCount);
    }


    @Test
    public void test_annotation() {

        OkHandler failOneThenSuccessHandler = new OkHandlerImpl();

        OkHandler handler = AopFactory233.CGLIB.proxyByAnnotation(failOneThenSuccessHandler);

        int count = handler.count();

        Assert.assertEquals(1, count);
    }
}