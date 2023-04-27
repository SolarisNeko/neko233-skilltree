package com.neko233.skilltree.commons.core.pipeline;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 【处理流】一个数据，会被所有节点处理
 *
 * @author SolarisNeko
 * Date on 2022-12-15
 */
public abstract class AbstractPipeline<T> {

    private final List<PipelineNode<T>> pipeline = new CopyOnWriteArrayList<>();

    public T handle(T input) {
        T result = input;
        for (PipelineNode<T> node : pipeline) {
            result = node.handle(result);
        }
        return result;
    }

    public AbstractPipeline<T> addPipelineNode(PipelineNode<T> node) {
        this.pipeline.add(node);
        return this;
    }

    public AbstractPipeline<T> removePipelineNode(PipelineNode<T> node) {
        this.pipeline.remove(node);
        return this;
    }

}
