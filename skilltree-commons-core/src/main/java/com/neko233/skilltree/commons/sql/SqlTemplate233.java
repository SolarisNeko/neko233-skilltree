package com.neko233.skilltree.commons.sql;


import com.neko233.json.JSON;
import com.neko233.skilltree.commons.core.base.StringUtils233;
import com.neko233.skilltree.commons.json.JsonUtils233;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple Handle SQL batch replace
 *
 * @author SolarisNeko on 2022-08-01
 **/
public class SqlTemplate233 {

    private static final String SQL_INJECT_REGEX = ".*(exec|insert|select|delete|drop|update|count|%|chr|mid|master|truncate|char|declare|;) .*";

    private final String sqlTemplate;
    private final Map<String, String> kvMap = new HashMap<>();

    public SqlTemplate233(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public static boolean isValueSqlInject(String sql) {
        return sql.toLowerCase().matches(SQL_INJECT_REGEX);
    }

    public static SqlTemplate233 builder(String sqlTemplate) {
        if (StringUtils233.isBlank(sqlTemplate)) {
            throw new RuntimeException("your sql template is blank !");
        }
        return new SqlTemplate233(sqlTemplate);
    }

    public SqlTemplate233 put(String key,
                              Object value) {
        kvMap.put(key, translateValue(value, false));
        return this;
    }


    /**
     * set kv
     *
     * @param key        key
     * @param value      value
     * @param isOriginal 是否原始值
     * @return this
     */
    public SqlTemplate233 put(String key,
                              Object value,
                              boolean isOriginal) {
        kvMap.put(key, translateValue(value, isOriginal));
        return this;
    }

    public SqlTemplate233 putAll(Map<String, Object> kvMap) {
        return putAll(kvMap, false);
    }

    /**
     * set kv
     *
     * @param kvMap      key-value map
     * @param isOriginal 是否原始值
     * @return this
     */
    public SqlTemplate233 putAll(Map<String, Object> kvMap,
                                 boolean isOriginal) {
        for (Map.Entry<String, Object> en : kvMap.entrySet()) {
            this.kvMap.put(en.getKey(), translateValue(en.getValue(), isOriginal));
        }
        return this;
    }

    public String translateValue(Object valueObj) {
        return translateValue(valueObj, false);
    }

    public String translateValue(Object valueObj,
                                 boolean isOriginal) {
        String value;
        if (isOriginal) {
            return String.valueOf(valueObj);
        }
        if (valueObj instanceof String) {
            value = "'" + String.valueOf(valueObj).replaceAll("'", "''") + "'";
        } else {
            value = String.valueOf(valueObj);
        }
        return value;
    }

    public SqlTemplate233 merge(String key,
                                Object value) {
        return merge(key, value, "");
    }

    public SqlTemplate233 merge(String key,
                                Object value,
                                String union) {
        kvMap.merge(key, translateValue(value), (v1, v2) -> v1 + union + v2);
        return this;
    }

    public String build() {
        return this.toString();
    }

    @Override
    public String toString() {
        String tempSql = sqlTemplate;
        // 替换全部
        for (Map.Entry<String, String> kv : kvMap.entrySet()) {
            String value = kv.getValue();
            boolean isSqlInject = isValueSqlInject(value);
            if (isSqlInject) {
                String msg = StringUtils233.format("[SqlTemplate] SQL Inject Error. Please check. sql Template = {}. kvMap = {}"
                        , sqlTemplate, JsonUtils233.toJsonString(kvMap));
                throw new IllegalArgumentException(msg);
            }
            tempSql = tempSql.replaceAll(
                    "\\$\\{" + kv.getKey() + "\\}",
                    value
            );
        }
        return tempSql;
    }
}