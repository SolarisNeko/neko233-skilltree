package com.neko233.skilltree.commons.algorithm.bstree;

/**
 * value = 对象
 */
public class BstNode<T> {
    public BstNode parent;
    public T element;
    public BstNode left;
    public BstNode right;

    @SuppressWarnings("unused")
    public BstNode(T element, BstNode<T> parent) {
        this.element = element;
        this.parent = parent;
    }
}