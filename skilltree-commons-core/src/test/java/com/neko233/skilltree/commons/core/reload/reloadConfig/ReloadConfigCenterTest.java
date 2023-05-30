package com.neko233.skilltree.commons.core.reload.reloadConfig;

import com.neko233.skilltree.commons.core.reload.reloadConfig.callback.impl.LogConfigChangeListener;
import com.neko233.skilltree.commons.core.reload.reloadConfig.impl.UserConfig;
import org.junit.Test;

public class ReloadConfigCenterTest {


    @Test
    public void reloadAll() throws InterruptedException {

        ReloadConfigCenter.instance.addCallbackListener(new LogConfigChangeListener());

        UserConfig userConfig = new UserConfig();
        System.out.println(userConfig.getConfigList());

        ReloadConfigCenter.instance.startReloadScheduler();

//        TimeUnit.SECONDS.sleep(60);
//        System.out.println(userConfig.getConfigList());


    }
}