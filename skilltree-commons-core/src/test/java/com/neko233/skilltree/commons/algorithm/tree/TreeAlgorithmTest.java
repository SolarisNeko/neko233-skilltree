package com.neko233.skilltree.commons.algorithm.tree;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TreeAlgorithmTest {

    @Test
    public void test1() {
        List<TreeNode233<String>> simpleTreeNode233s = new ArrayList<TreeNode233<String>>() {{
            SimpleTreeNode233<String> build1 = new SimpleTreeNode233<String>() {{
                guid(1L);
                setNodeId(1);
                setParentId(0);
                setData("neko-1");
            }};
            TreeNode233<String> build2 = new SimpleTreeNode233<String>() {{
                guid(2L);
                setNodeId(11);
                setParentId(1);
                setData("neko-1-1");
            }};
            TreeNode233<String> build3 = new SimpleTreeNode233<String>() {{
                guid(3L);
                setNodeId(12);
                setParentId(1);
                setData("neko-1-2");
            }};
            add(build1);
            add(build2);
            add(build3);
        }};

        StopWatch watcher = new StopWatch();
        watcher.start();
        boolean isAccelerate = true;
        for (int i = 0; i < 1; i++) {
            List<TreeNode233<String>> simpleTreeNode233S1 = TreeAlgorithm.listToTree(simpleTreeNode233s, isAccelerate);
        }
        // last one
        List<TreeNode233<String>> treeNodeList = TreeAlgorithm.listToTree(simpleTreeNode233s, isAccelerate);
        System.out.println(JSON.toJSONString(treeNodeList));

        watcher.stop();
        log.info("spend {} ms", watcher.getTime(TimeUnit.MILLISECONDS));

        List<TreeNode233<String>> linerList = TreeAlgorithm.treeToLineList(treeNodeList);
        System.out.println(linerList);
    }


}