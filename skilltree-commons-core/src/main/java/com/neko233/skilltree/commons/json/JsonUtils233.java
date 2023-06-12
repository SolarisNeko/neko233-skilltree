package com.neko233.skilltree.commons.json;

import com.neko233.json.JSON;
import com.neko233.json.convert.PrettyJsonConfig;

import java.util.List;

public class JsonUtils233 {

    /**
     * json String
     *
     * @param obj 对象
     * @return json string
     */
    public static String toJsonString(Object obj) {
        return JSON.serialize(obj);
    }

    public static String toJsonStringPretty(Object obj) {
        return JSON.serialize(obj, PrettyJsonConfig.instance);
    }

    public static <T> T parseToObject(String text, Class<T> clazz) throws Exception {
        return JSON.deserialize(text, clazz);
    }

    public static <T> List<T> parseToObjectList(String text, Class<T> clazz) throws Exception {
        return JSON.deserializeArray(text, clazz);
    }

}
