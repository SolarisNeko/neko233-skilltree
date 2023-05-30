package com.neko233.skilltree.commons.core.reload.reloadConfig.callback.impl;

import com.neko233.skilltree.commons.core.reload.reloadConfig.RefreshableConfig;
import com.neko233.skilltree.commons.core.reload.reloadConfig.callback.ConfigChangeListener;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LuoHaoJun on 2023-05-30
 **/
@Slf4j
public class LogConfigChangeListener implements ConfigChangeListener {

    private final Map<String, Long> configNameReloadStartMs = new HashMap<>();

    @Override
    public String getListenerName() {
        return "logger";
    }

    @Override
    public void reloadDataBefore(RefreshableConfig<?> config) {
        configNameReloadStartMs.put(config.getName(), System.currentTimeMillis());
        log.info("config reload before. configName = {}", config.getName());
    }

    @Override
    public void reloadDataAfter(RefreshableConfig<?> config) {
        log.info("config reload after. configName = {}", config.getName());
    }

    @Override
    public void reloadDataFinish(RefreshableConfig<?> config) {
        long endMs = System.currentTimeMillis();
        long startMs = configNameReloadStartMs.getOrDefault(config.getName(), endMs);
        long keepMs = endMs - startMs;

        log.info("config reload finish. configName = {}, spendTimeMs = {}", config.getName(), keepMs);
    }
}
