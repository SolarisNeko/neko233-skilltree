package com.neko233.skilltree.ioc.provider;

import com.neko233.skilltree.ioc.annotation.Component;
import com.neko233.skilltree.ioc.constant.ComponentType;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LuoHaoJun on 2023-05-16
 **/
@Component(componentType = ComponentType.PROTOTYPE)
@Data
public class ProviderByPrototype {

    public static final AtomicInteger counter = new AtomicInteger(1);

    private String name = "provider-prototype-" + counter.getAndIncrement();


}
