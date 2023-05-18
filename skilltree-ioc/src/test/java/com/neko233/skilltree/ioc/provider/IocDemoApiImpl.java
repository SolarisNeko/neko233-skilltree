package com.neko233.skilltree.ioc.provider;

import com.neko233.skilltree.ioc.annotation.Provider;
import com.neko233.skilltree.ioc.api.IocDemoApi;

@Provider
public class IocDemoApiImpl implements IocDemoApi {
    @Override
    public String sayHello() {
        return "hello";
    }
}
