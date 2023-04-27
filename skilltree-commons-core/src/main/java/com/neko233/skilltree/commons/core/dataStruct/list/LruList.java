package com.neko233.skilltree.commons.core.dataStruct.list;

import com.neko233.skilltree.commons.core.base.ChooseUtils233;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * lru List
 */
@ToString
@EqualsAndHashCode
public class LruList<V> {

    private Node<V> first;

    private Node<V> last;

    private int capacity = 10;

    private final Map<String, Node<V>> data = new ConcurrentHashMap<>();

    public LruList(int capacity) {
        this.capacity = ChooseUtils233.choose(capacity <= 0, 10, capacity);
        first = new Node<>();
        last = new Node<>();
        first.next = last;
        last.prev = first;
    }

    public LruList() {
        this(10);
    }

    public V get(String key) {
        if (key == null) {
            return null;
        }
        Node<V> node = data.get(key);
        if (node != null) {
            //node的相邻节点双向指针指向对方
            node.prev.next = node.next;
            node.next.prev = node.prev;

            //将node插入到first后
            node.next = first.next;
            node.prev = first;
            first.next.prev = node;
            first.next = node;
            return node.value;
        }
        return null;
    }


    public void put(String key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("param is null");
        }
        Node<V> node = data.get(key);
        if (node == null) {
            if (data.size() >= capacity) {
                Node<V> nodeRemoved = last.prev;
                data.remove(nodeRemoved.key);
                nodeRemoved.prev.next = last;
                last.prev = nodeRemoved.prev;
            }
            node = new Node<>(key, value);
            data.put(key, node);
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        //将node插入到first后面
        node.next = first.next;
        node.prev = first;
        first.next.prev = node;
        first.next = node;
    }


    /**
     * 节点类
     */
    static class Node<V> {
        private String key;

        private V value;

        private Node<V> prev;

        private Node<V> next;

        Node() {
        }

        Node(String key, V value) {
            this.key = key;
            this.value = value;
        }

    }
}
