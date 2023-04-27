package com.neko233.skilltree.commons.algorithm.tree;

import java.util.List;

/**
 * recommend Unique key = parentId, nodeId (because same nodeId can multi parentId)
 *
 * @author SolarisNeko on 2023-01-01
 **/
public interface TreeNode233<T> {


    /**
     * 全局唯一ID, 因为 nodeId 可以有多个父
     *
     * @return global unique ID
     */
    Long guid();

    /**
     * set guid
     *
     * @param guid global unique ID
     */
    void guid(Long guid);


    /**
     * 自己节点 id | not unique
     *
     * @return nodeId
     */
    Integer nodeId();


    /**
     * score
     *
     * @return 排序分值
     */
    Integer sortScore();


    /**
     * 父节点 ID
     *
     * @return parent id
     */
    Integer parentId();


    /**
     * children
     *
     * @return children
     */
    List<TreeNode233<T>> children();


    /**
     * set children
     *
     * @param children 孩子节点
     */
    void setChildrenIds(List<TreeNode233<T>> children);


    /**
     * 是否使用
     *
     * @return bool
     */
    boolean isUse();


}
