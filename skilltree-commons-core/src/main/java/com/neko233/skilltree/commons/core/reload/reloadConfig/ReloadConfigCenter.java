package com.neko233.skilltree.commons.core.reload.reloadConfig;

import com.neko233.skilltree.commons.core.reload.reloadConfig.callback.ConfigChangeListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ReloadConfigCenter {

    public static final ReloadConfigCenter instance = new ReloadConfigCenter();

    private final List<ConfigChangeListener> configChangeListenerList = new ArrayList<>();
    private final Map<String, RefreshableConfig<?>> configNameToConfigMap = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledFuture;
    private final AtomicBoolean isStartScheduler = new AtomicBoolean(false);


    public void register(String configName, RefreshableConfig<?> refreshableConfig) {
        configNameToConfigMap.merge(configName, refreshableConfig, (v1, v2) -> {
            log.error("configName bind config is duplicate! 唯一配置名, 绑定的配置重复. 请检查实现的子类是不是 Singleton. configName = {}", configName);
            return v1;
        });

    }

    public void addCallbackListener(ConfigChangeListener configChangeListener) {
        configChangeListenerList.add(configChangeListener);
    }

    public void removeCallbackListener(ConfigChangeListener configChangeListener) {
        configChangeListenerList.remove(configChangeListener);
    }

    public void reloadAll() {
        for (String configName : configNameToConfigMap.keySet()) {
            reload(configName);
        }
    }

    public void reload(String configName) {
        RefreshableConfig<?> refreshableConfig = configNameToConfigMap.get(configName);
        if (refreshableConfig == null) {
            log.error("not found config. configName = {}", configName);
            return;
        }

        reloadBefore(refreshableConfig);

        reloadData(refreshableConfig);

        reloadAfter(refreshableConfig);

    }


    /**
     * reload data
     *
     * @param config 配置
     */
    private void reloadData(RefreshableConfig<?> config) {
        config.reloadConfigData();
    }

    /**
     * Lifecycle - before
     *
     * @param config 配置
     */
    private void reloadBefore(RefreshableConfig<?> config) {
        for (ConfigChangeListener configChangeListener : configChangeListenerList) {
            configChangeListener.reloadDataBefore(config);
        }
        config.before();
    }

    /**
     * Lifecycle - after
     *
     * @param config 配置
     */
    private void reloadAfter(RefreshableConfig<?> config) {
        for (ConfigChangeListener configChangeListener : configChangeListenerList) {
            configChangeListener.reloadDataAfter(config);
        }
        config.after();
    }

    public void stopReloadScheduler() {
        if (scheduledFuture == null) {
            return;
        }

        if (!isStartScheduler.compareAndSet(true, false)) {
            log.debug("已经暂停了调度了, 不需要重复暂停");
            return;
        }
        scheduledFuture.cancel(false);
    }

    public void startReloadScheduler() {
        if (!isStartScheduler.compareAndSet(false, true)) {
            log.debug("已经开始调度了, 不需要重复调度");
            return;
        }


        scheduler = Executors.newScheduledThreadPool(1);

        this.scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
            reloadAll();
        }, 0, 30, TimeUnit.SECONDS);
    }
}