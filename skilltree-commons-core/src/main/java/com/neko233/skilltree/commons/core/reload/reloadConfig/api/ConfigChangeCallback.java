package com.neko233.skilltree.commons.core.reload.reloadConfig.api;


/**
 * 配置变更回调接口
 *
 * @param <T>
 */
@FunctionalInterface
public interface ConfigChangeCallback<T> {

    void onConfigChange(T newConfig);

}
