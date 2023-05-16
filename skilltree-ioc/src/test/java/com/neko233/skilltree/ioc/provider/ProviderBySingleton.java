package com.neko233.skilltree.ioc.provider;

import com.neko233.skilltree.ioc.annotation.Component;
import com.neko233.skilltree.ioc.constant.ComponentType;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LuoHaoJun on 2023-05-16
 **/
@Component(componentType = ComponentType.SINGLETON)
@Data
public class ProviderBySingleton {

    public static final AtomicInteger counter = new AtomicInteger(1);

    private String name = "provider-singleton-" + counter.getAndIncrement();


}
