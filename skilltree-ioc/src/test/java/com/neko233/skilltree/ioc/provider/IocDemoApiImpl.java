package com.neko233.skilltree.ioc.provider;

import com.neko233.skilltree.ioc.annotation.Component;
import com.neko233.skilltree.ioc.api.IocDemoApi;

@Component
public class IocDemoApiImpl implements IocDemoApi {
    @Override
    public String sayHello() {
        return "hello";
    }
}
