package com.neko233.skilltree.commons.core.reload.reloadConfig.callback;


import com.neko233.skilltree.commons.core.reload.reloadConfig.RefreshableConfig;

// 简单的配置变更监听器类，模拟注册和触发配置变更事件
public interface ConfigChangeListener {

    String getListenerName();

    /**
     * 前置阶段
     */
    void reloadDataBefore(RefreshableConfig<?> config);


    /**
     * 后置阶段
     */
    void reloadDataAfter(RefreshableConfig<?> config);

    /**
     * 完成
     */
    void reloadDataFinish(RefreshableConfig<?> config);

}