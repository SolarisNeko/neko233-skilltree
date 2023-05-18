package com.neko233.skilltree.ioc.provider;

import com.neko233.skilltree.ioc.annotation.Provider;
import com.neko233.skilltree.ioc.constant.ProvideType;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SolarisNeko on 2023-05-01
 **/
@Provider(type = ProvideType.PROTOTYPE)
@Data
public class ProviderByPrototype {

    public static final AtomicInteger counter = new AtomicInteger(1);

    private String name = "provider-prototype-" + counter.getAndIncrement();


}
