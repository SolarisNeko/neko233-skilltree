package com.neko233.toolchain.commons.algorithm.tree;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SolarisNeko on 2023-01-01
 **/
@Slf4j
public class TreeAlgorithm {


    public static <T> List<TreeNode233<T>> listToTree(List<TreeNode233<T>> nodeList) {
        return listToTree(nodeList, false);
    }

    /**
     * @param allNodeList  所有节点列表
     * @param isAccelerate 是否加速
     * @param <T>          泛型
     * @return tree root Node List
     */
    public static <T> List<TreeNode233<T>> listToTree(List<TreeNode233<T>> allNodeList, boolean isAccelerate) {
        Map<Integer, List<TreeNode233<T>>> parentIdMap;
        if (isAccelerate) {
            parentIdMap = allNodeList.stream().collect(Collectors.groupingBy(TreeNode233::parentId));
        } else {
            parentIdMap = null;
        }
        if (parentIdMap == null) {
            return new ArrayList<>();
        }

        // 组装成父子的树形结构
        return allNodeList.stream()
                .filter(node -> node.parentId() == 0)
                .peek(node -> {
                    List<TreeNode233<T>> children;
                    if (isAccelerate) {
                        children = getChildren(node, parentIdMap);
                    } else {
                        children = getChildren(node, allNodeList);
                    }
                    node.setChildrenIds(children);
                })
                .sorted(Comparator.comparingInt(node -> (node.sortScore() == null ? 0 : node.sortScore())))
                .collect(Collectors.toList());
    }


    /**
     * @param root        相对root
     * @param allNodeList 所有数据
     * @return children Node List
     */
    public static <T> List<TreeNode233<T>> getChildren(TreeNode233<T> root, List<TreeNode233<T>> allNodeList) {
        return allNodeList.stream()
                .filter(node -> {
                    try {
                        // core step
                        return node.parentId().equals(root.nodeId());
                    } catch (Exception e) {
                        log.error("list -> tree. happen error", e);
                        return false;
                    }
                })
                .peek(node -> {
                    List<TreeNode233<T>> children = getChildren(node, allNodeList);
                    node.setChildrenIds(children);
                })
                .sorted(Comparator.comparingInt(menu -> (menu.sortScore() == null ? 0 : menu.sortScore())))
                .collect(Collectors.toList());
    }

    private static <T> List<TreeNode233<T>> getChildren(TreeNode233<T> root, Map<Integer, List<TreeNode233<T>>> parentIdMap) {
        return parentIdMap.get(root.nodeId());
    }


    public static <T> List<TreeNode233<T>> treeToLineList(List<TreeNode233<T>> treeList) {
        return treeToLineList(treeList, false);
    }

    /**
     * tree List -> line List
     *
     * @param treeList   多个树的 root 节点
     * @param isParallel 是否并行
     * @return 线性化的 List, 平铺 Node
     */
    public static <T> List<TreeNode233<T>> treeToLineList(List<TreeNode233<T>> treeList, boolean isParallel) {
        Stream<TreeNode233<T>> stream;
        if (isParallel) {
            stream = treeList.parallelStream();
        } else {
            stream = treeList.stream();
        }
        return new ArrayList<>(stream
                .map(TreeNode233::children)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(List::stream)
                // distinct
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TreeNode233::guid))),
                                ArrayList::new)
                ));

    }


}
