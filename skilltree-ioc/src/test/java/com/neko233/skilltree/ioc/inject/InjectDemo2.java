package com.neko233.skilltree.ioc.inject;

import com.neko233.skilltree.ioc.annotation.Component;
import com.neko233.skilltree.ioc.annotation.Inject;
import com.neko233.skilltree.ioc.provider.ProviderByPrototype;
import com.neko233.skilltree.ioc.provider.ProviderBySingleton;
import lombok.Data;

/**
 * @author SolarisNeko on 2023-05-01
 **/
@Data
@Component
public class InjectDemo2 {


    @Inject
    public ProviderBySingleton providerBySingleton;

    @Inject
    public ProviderByPrototype providerByPrototype;

}
