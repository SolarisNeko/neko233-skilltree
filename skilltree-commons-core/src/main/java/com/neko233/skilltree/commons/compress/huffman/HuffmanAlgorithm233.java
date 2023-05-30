package com.neko233.skilltree.commons.compress.huffman;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Huffman Algorithm 哈夫曼编码压缩算法
 *
 * @author SolarisNeko on 2023-01-01
 */
public class HuffmanAlgorithm233 {

    // 哈夫曼解压
    public static byte[] unzip(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, b));
        }

        // 解码,反向编码表
        HashMap<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }

        //根据编码扫描到对应的 ASCII 码对应的字符
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;
            boolean flag = true;
            Byte b = null;
            while (flag) {
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count;
        }

        byte b[] = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;

    }

    // 转化二进制
    private static String byteToBitString(boolean flag, byte b) {
        int temp = b;
        if (flag) {
            temp |= 256;
        }
        String str = Integer.toBinaryString(temp);
        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }


    public static HuffmanResult zip(String content) {
        return zip(content.getBytes(StandardCharsets.UTF_8));
    }

    // 哈夫曼编码压缩
    public static HuffmanResult zip(byte[] bytes) {
        HuffmanAlgorithm233 huffmanAlgorithm233 = new HuffmanAlgorithm233();
        List<HuffmanNode> huffmanNodes = getNodes(bytes);
        // 哈夫曼树
        HuffmanNode huffmanTree = createHuffmanTree(huffmanNodes);
        // 哈夫曼编码表
        Map<Byte, String> huffmanCodes = huffmanAlgorithm233.getCodes(huffmanTree);
        // 哈夫曼编码
        byte[] zip = zip(bytes, huffmanCodes);
        return HuffmanResult.builder()
                .zip(zip)
                .huffmanPathTree(huffmanCodes)
                .build();
    }

    // 压缩
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
        int len;
        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length() / 8;
        } else {
            len = stringBuilder.length() / 8 + 1;
        }
        byte[] by = new byte[len];
        int index = 0;
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 > stringBuilder.length()) {
                strByte = stringBuilder.substring(i);
                by[index] = (byte) Integer.parseInt(strByte, 2);
                index++;
            } else {
                strByte = stringBuilder.substring(i, i + 8);
                by[index] = (byte) Integer.parseInt(strByte, 2);
                index++;
            }
        }
        return by;
    }

    private Map<Byte, String> huffmanCodes = new HashMap<>();
    private StringBuilder stringBuilder = new StringBuilder();

    //重载
    private Map<Byte, String> getCodes(HuffmanNode root) {
        if (root == null) {
            return null;
        }
        getCodes(root.left, "0", stringBuilder);
        getCodes(root.right, "1", stringBuilder);
        return huffmanCodes;
    }

    // 获取哈夫曼编码
    private void getCodes(HuffmanNode huffmanNode, String code, StringBuilder stringBuilder) {
        StringBuilder builder = new StringBuilder(stringBuilder);
        builder.append(code);
        if (huffmanNode != null) {
            if (huffmanNode.data == null) {  //递归
                getCodes(huffmanNode.left, "0", builder);
                getCodes(huffmanNode.right, "1", builder);
            } else {
                huffmanCodes.put(huffmanNode.data, builder.toString());
            }
        }
    }

    //前序遍历
    private static void preOrder(HuffmanNode root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("哈夫曼树为空");
        }
    }

    //生成哈夫曼树
    private static HuffmanNode createHuffmanTree(List<HuffmanNode> huffmanNodes) {
        while (huffmanNodes.size() > 1) {
            Collections.sort(huffmanNodes);

            HuffmanNode leftHuffmanNode = huffmanNodes.get(0);
            HuffmanNode rightHuffmanNode = huffmanNodes.get(1);

            HuffmanNode parent = new HuffmanNode(null, leftHuffmanNode.weight + rightHuffmanNode.weight);
            parent.left = leftHuffmanNode;
            parent.right = rightHuffmanNode;

            huffmanNodes.remove(leftHuffmanNode);
            huffmanNodes.remove(rightHuffmanNode);
            huffmanNodes.add(parent);
        }
        return huffmanNodes.get(0);
    }

    //接收字节数组
    private static List<HuffmanNode> getNodes(byte[] bytes) {
        List<HuffmanNode> huffmanNodes = new ArrayList<>();
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }
        //遍历map
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            huffmanNodes.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        return huffmanNodes;
    }
}

class HuffmanNode implements Comparable<HuffmanNode> {
    Byte data;
    int weight; //字符出现的次数
    HuffmanNode left;
    HuffmanNode right;

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    public HuffmanNode(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        //从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}
