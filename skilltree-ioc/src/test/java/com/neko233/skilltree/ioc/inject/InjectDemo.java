package com.neko233.skilltree.ioc.inject;

import com.neko233.skilltree.ioc.annotation.Component;
import com.neko233.skilltree.ioc.annotation.Inject;
import com.neko233.skilltree.ioc.provider.ProviderByPrototype;
import com.neko233.skilltree.ioc.provider.ProviderBySingleton;
import lombok.Data;

/**
 * @author LuoHaoJun on 2023-05-16
 **/
@Data
@Component
public class InjectDemo {

    @Inject
    public ProviderBySingleton providerBySingleton;

    @Inject
    public ProviderByPrototype providerByPrototype;

}
