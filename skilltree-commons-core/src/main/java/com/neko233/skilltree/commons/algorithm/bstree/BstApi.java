package com.neko233.skilltree.commons.algorithm.bstree;

public interface BstApi<T> {
    /**
     * who is the root node
     */
    T root();

    /**
     * how to get the left child of the node
     */
    T left(T node);

    /**
     * how to get the right child of the node
     */
    T right(T node);

    /**
     * how to print the node
     */
    T data(T node);
}
