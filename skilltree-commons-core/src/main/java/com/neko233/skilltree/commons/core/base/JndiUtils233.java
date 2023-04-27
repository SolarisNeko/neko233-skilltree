package com.neko233.skilltree.commons.core.base;


import com.neko233.skilltree.commons.core.exception.UtilException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.Map;

/**
 * JNDI工具类<br>
 * JNDI是Java Naming and Directory Interface（JAVA命名和目录接口）的英文简写，<br>
 * 为 JAVA App 提供命名和目录访问服务的 API（Application Programing Interface，应用程序编程接口）。
 */
public class JndiUtils233 {

    /**
     * 创建{@link InitialDirContext}
     *
     * @param environment 环境参数，{code null}表示无参数
     * @return {@link InitialDirContext}
     */
    public static InitialDirContext createInitialDirContext(Map<String, String> environment) {
        try {
            if (MapUtils233.isEmpty(environment)) {
                return new InitialDirContext();
            }
            return new InitialDirContext(new Hashtable<>(environment));
        } catch (NamingException e) {
            throw new UtilException(e);
        }
    }

    /**
     * 创建{@link InitialContext}
     *
     * @param environment 环境参数，{code null}表示无参数
     * @return {@link InitialContext}
     */
    public static InitialContext createInitialContext(Map<String, String> environment) {
        try {
            if (MapUtils233.isEmpty(environment)) {
                return new InitialContext();
            }
            return new InitialContext(new Hashtable<>(environment));
        } catch (NamingException e) {
            throw new UtilException(e);
        }
    }

    /**
     * 获取指定容器环境的对象的属性<br>
     * 如获取DNS属性，则URI为类似：dns:hutool.cn
     *
     * @param uri     URI字符串，格式为[scheme:][name]/[domain]
     * @param attrIds 需要获取的属性ID名称
     * @return {@link Attributes}
     */
    public static Attributes getAttributes(String uri, String... attrIds) {
        try {
            return createInitialDirContext(null).getAttributes(uri, attrIds);
        } catch (NamingException e) {
            throw new UtilException(e);
        }
    }
}
