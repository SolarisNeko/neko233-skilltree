package com.neko233.skilltree.commons.core.pipeline;

/**
 * @author SolarisNeko
 * Date on 2022-12-15
 */
public interface PipelineNode<T> {

    T handle(T input);

}
