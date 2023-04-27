package com.neko233.skilltree.commons.core.approvalChain;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 【审批链】只会有一个节点处理生效
 *
 * @author SolarisNeko
 * Date on 2022-12-15
 */
public abstract class AbstractApprovalChain<INPUT, OUTPUT> {

    private final List<ApprovalChainNode<INPUT, OUTPUT>> pipeline = new CopyOnWriteArrayList<>();

    public OUTPUT handle(INPUT input) {
        for (ApprovalChainNode<INPUT, OUTPUT> node : pipeline) {
            if (node.isItHandle()) {
                return node.handle(input);
            }

        }
        return null;
    }

    public AbstractApprovalChain<INPUT, OUTPUT> addPipelineNode(ApprovalChainNode<INPUT, OUTPUT> node) {
        this.pipeline.add(node);
        return this;
    }

    public AbstractApprovalChain<INPUT, OUTPUT> removePipelineNode(ApprovalChainNode<INPUT, OUTPUT> node) {
        this.pipeline.remove(node);
        return this;
    }

}
