package com.neko233.skilltree.commons.core.reload.reloadConfig;

import com.neko233.skilltree.commons.core.base.MapUtils233;

import java.util.List;
import java.util.Map;

/**
 * 可刷新的配置 <br>
 * 约定: 子类需要单例 (否则经常有错误日志)
 *
 * @param <T>
 */
public abstract class RefreshableConfig<T> {


    public List<T> configList;


    public RefreshableConfig() {

        // 初始化配置
        reloadConfigData();

        registerToConfigCenter();
    }

    /**
     * 注册到【配置中心】
     */
    private void registerToConfigCenter() {
        ReloadConfigCenter.instance.register(getName(), this);
    }

    /**
     * 刷新配置
     */
    public void refreshConfig() {
        // 从配置中心重新加载配置
        reloadConfigData();
    }


    public List<T> getConfigList() {
        return configList;
    }


    /**
     * @return 名字
     */
    public abstract String getName();


    /**
     * 获取配置元数据
     *
     * @return Map
     */
    public abstract Map<String, String> getConfigMetadata();

    public Map<String, String> getConfigMetadataNotNull() {
        return getConfigMetadata() == null ? MapUtils233.of() : getConfigMetadata();
    }


    public abstract void before();

    /**
     * 从[配置中心]加载配置
     */
    public abstract void reloadConfigData();

    public abstract void after();

}

