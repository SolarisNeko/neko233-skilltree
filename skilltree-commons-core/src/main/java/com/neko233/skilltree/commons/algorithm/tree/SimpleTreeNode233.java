package com.neko233.skilltree.commons.algorithm.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SolarisNeko on 2023-01-01
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleTreeNode233<T> implements TreeNode233<T> {

    private Long guid;
    private String type;
    private Integer nodeId; // 0 = root
    private Integer parentId; // 0 = root
    private Integer sortScore;

    private List<TreeNode233<T>> childrenIds;
    private T data;
    private boolean isUse = false;

    @Override
    public Long guid() {
        if (this.guid == null) {
            throw new IllegalArgumentException("your guid is null! please check");
        }
        return this.guid;
    }

    @Override
    public void guid(Long guid) {
        this.guid = guid;
    }

    @Override
    public Integer nodeId() {
        return nodeId;
    }

    @Override
    public Integer parentId() {
        return parentId;
    }

    @Override
    public List<TreeNode233<T>> children() {
        return childrenIds;
    }

    @Override
    public Integer sortScore() {
        return sortScore;
    }

    @Override
    public boolean isUse() {
        return isUse;
    }

}
