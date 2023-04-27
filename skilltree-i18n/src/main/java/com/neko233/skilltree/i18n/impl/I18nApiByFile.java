package com.neko233.skilltree.i18n.impl;

import com.neko233.skilltree.commons.core.base.PropertiesUtils233;
import com.neko233.skilltree.commons.core.base.StringUtils233;
import com.neko233.skilltree.i18n.I18nApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class I18nApiByFile implements I18nApi {

    private Properties currentProperties;
    private boolean isFromClassPath;
    private String i18nName;
    private String fullPathName;

    @Override
    public Properties load(String i18nName,
                           String fullPathName) {
        Properties i18nProp;
        if (StringUtils233.isNotBlank(fullPathName)) {
            isFromClassPath = false;
            i18nProp = PropertiesUtils233.loadProperties(fullPathName, isFromClassPath);
        } else {
            isFromClassPath = true;
            i18nProp = PropertiesUtils233.loadProperties(i18nName, isFromClassPath);
        }
        this.currentProperties = Optional.ofNullable(i18nProp).orElse(new Properties());
        return i18nProp;
    }


    @Override
    public Properties getAll() {
        return this.currentProperties;
    }

    @Override
    public Map<String, String> get(Collection<String> keys) {
        Map<String, String> kv = new HashMap<>();
        for (String key : keys) {
            String value = this.currentProperties.getProperty(key);
            kv.put(key, value);
        }
        return kv;
    }

    @Override
    public boolean set(Map<String, String> kvMap) {
        for (Map.Entry<String, String> kv : kvMap.entrySet()) {
            this.currentProperties.setProperty(kv.getKey(), kv.getValue());
        }

        List<String> propertiesString = this.currentProperties.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.toList());
        File file;
        if (isFromClassPath) {
            URL resource = this.getClass().getResource("/" + i18nName);
            if (resource == null) {
                return false;
            }
            String fileName = resource.getFile();
            file = new File(fileName);
        } else {
            file = new File(fullPathName);
        }

        if (!file.exists()) {
            return false;
        }

        // overwrite
        try {
            FileUtils.writeLines(file, propertiesString);
        } catch (IOException e) {
            log.error("file IO error. fileName = {}", file.getName(), e);
            return false;
        }

        return true;
    }

}
