package com.neko233.skilltree.ioc;


import com.neko233.skilltree.ioc.api.IocDemoApi;
import com.neko233.skilltree.ioc.inject.InjectDemo;
import com.neko233.skilltree.ioc.inject.InjectDemo2;
import com.neko233.skilltree.ioc.provider.IocDemoApiImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko on 2023-05-01
 **/
public class IocContainerTest {

    @Test
    public void scan() throws Exception {
        IocContainer233 iocContainer = new IocContainer233();
        iocContainer.scan(IocContainerTest.class);

        InjectDemo bean = iocContainer.getObject(InjectDemo.class);
        System.out.println(bean);

        InjectDemo2 bean2 = iocContainer.getObject(InjectDemo2.class);
        System.out.println(bean2);

        IocDemoApi iocDemoApi = iocContainer.getObject(IocDemoApiImpl.class);
        Assert.assertEquals("hello", iocDemoApi.sayHello());
    }
}