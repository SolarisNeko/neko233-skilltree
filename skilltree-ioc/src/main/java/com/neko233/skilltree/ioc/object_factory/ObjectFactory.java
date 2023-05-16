package com.neko233.skilltree.ioc.object_factory;

/**
 * @author LuoHaoJun on 2023-05-16
 **/
public interface ObjectFactory {


    Object getOrCreate() throws Exception;

    Class<?> getTargetClass();
}
