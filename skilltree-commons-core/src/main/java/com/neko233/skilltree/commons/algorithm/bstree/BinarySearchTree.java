package com.neko233.skilltree.commons.algorithm.bstree;


import java.util.Comparator;

@SuppressWarnings("unchecked")
public class BinarySearchTree<T> implements BstApi {

    private Comparator<T> comparator;

    private BstNode<T> root;
    private int size;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public int getSize() {
        return size;
    }

    /**
     * BST Insert
     *
     * @param element 元素
     */
    public void addNode(T element) {
        if (element == null) {
            throw new IllegalArgumentException("element must be not null");
        }

        // init
        if (root == null) {
            root = new BstNode<T>(element, null);
            size++;
            return;
        }

        // add element and maintain BST
        BstNode<T> parent = root;
        BstNode<T> point = root;
        // BST core
        int cmp = 0;

        // recursive choose exists parent for new element
        while (point != null) {
            // 大小
            cmp = compare(element, point.element);
            parent = point;
            if (cmp > 0) {
                point = point.right;
            } else if (cmp < 0) {
                point = point.left;
            } else {
                // exit
                point.element = element;
                return;
            }
        }

        // if element not exists.
        BstNode<T> newBstNode = new BstNode<T>(element, parent);
        if (cmp > 0) {
            parent.right = newBstNode;
        } else {
            parent.left = newBstNode;
        }
        size++;
    }

    /**
     * 比较两个元素的大小
     */
    private int compare(T e1, T e2) {
        return (comparator != null)
                ? comparator.compare(e1, e2)
                : ((Comparable<T>) e1).compareTo(e2);
    }


    @Override
    public BstNode<T> root() {
        return root;
    }

    @Override
    public BstNode<T> left(Object node) {
        return ((BstNode<T>) node).left;
    }

    @Override
    public BstNode<T> right(Object node) {
        return ((BstNode<T>) node).right;
    }

    @Override
    public T data(Object node) {
        return ((BstNode<T>) node).element;
    }

}
