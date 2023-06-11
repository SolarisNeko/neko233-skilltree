package com.neko233.skilltree.commons.json;

import com.neko233.json.JSON;
import com.neko233.json.utils.BeanJsonOrmUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonUtils233 {

    /**
     * json String
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        return JSON.serialize(obj);
    }

    public static <T> T parseToObject(String text, Class<T> clazz) throws Exception {
        return JSON.deserialize(text, clazz);
    }

    public static <T> List<T> parseToObjectList(String text, Class<T> clazz) throws Exception {
        List<Map<String, Object>> maps = JSON.deserializeArray(text);
        return maps.stream()
                .map(map -> {
                    if (map == null) {
                        return null;
                    }
                    try {
                        return BeanJsonOrmUtils.mapToBean(map, clazz);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

}
