package com.neko233.skilltree.commons.core.reload.reloadConfig.impl;


import com.neko233.skilltree.commons.core.base.ListUtils233;
import com.neko233.skilltree.commons.core.reload.reloadConfig.RefreshableConfig;
import com.neko233.skilltree.commons.core.reload.reloadConfig.data.MockReloadUserData;

import java.util.Map;

// 实现一个具体的可自动刷新的用户配置类
public class UserConfig extends RefreshableConfig<MockReloadUserData> {

    int count = 0;

    @Override
    public String getName() {
        return "demo";
    }

    @Override
    public Map<String, String> getConfigMetadata() {
        return null;
    }

    @Override
    public void before() {

    }

    @Override
    public void reloadConfigData() {
        // 从配置中心加载用户配置
        // 这里只是示例，实际情况根据具体的配置中心实现方式进行加载

        if (count == 0) {
            configList = ListUtils233.of(
                    new MockReloadUserData("s1", 18),
                    new MockReloadUserData("s2", 22)
            );
        } else {
            configList = ListUtils233.of(
                    new MockReloadUserData("s1-reload", 18)
            );

        }

        count++;

    }

    @Override
    public void after() {

    }

}
