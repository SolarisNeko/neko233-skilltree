package com.neko233.skilltree.ioc.object_factory;

/**
 * @author SolarisNeko on 2023-05-01
 **/
public interface ObjectFactory {


    Object getOrCreate() throws Exception;

    Class<?> getTargetClass();
}
