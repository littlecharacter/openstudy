package com.lc.structure.tree.trie;

import java.util.HashMap;

/**
 * 前缀树，又叫字典树
 * 1，字符在路径上，即 nextMap 的 key
 * 2，字符出现的次数 和 字符作为字符串终点的次数在节点上，即 nextMap 的 value（Node）
 *
 * @author gujixian
 * @since 2022/12/2
 */
public class TrieTree {
    private final Node root;

    public TrieTree() {
        root = new Node();
    }

    public void insert(String word) {
        if (word == null) {
            return;
        }
        char[] wc = word.toCharArray();
        Node node = root;
        node.pass++;
        for (char c : wc) {
            if (!node.nextMap.containsKey(c)) {
                node.nextMap.put(c, new Node());
            }
            node = node.nextMap.get(c);
            node.pass++;
        }
        // 循环结束后，说明单词已经遍历完 + 字符在路径上，所以 node 就是字符的信息
        node.end++;
    }

    public void delete(String word) {
        if (search(word) == 0) {
            return;
        }
        Node node = root;
        char[] wc = word.toCharArray();
        for (char c : wc) {
            if (--node.nextMap.get(c).pass == 0) {
                node.nextMap.remove(c); //删除节点，防止内存泄露
                return;
            }
            node = node.nextMap.get(c);
        }
        node.end--;
    }

    // word 这个单词之前加入了几次
    public int search(String word) {
        if (word == null) {
            return 0;
        }
        char[] wc = word.toCharArray();
        Node node = root;
        for (char c : wc) {
            if (!node.nextMap.containsKey(c)) {
                return 0;
            }
            node = node.nextMap.get(c);
        }
        return node.end; // 有几个结尾，就加入了几次
    }

    //所有加入的字符串中，有几个是以pre 这个字符串作为前缀的
    public int startsWith(String prefix) {
        if (prefix == null) {
            return 0;
        }
        char[] pc = prefix.toCharArray();
        Node node = root;
        for (char c : pc) {
            Node next = node.nextMap.get(c);
            if (next == null) {
                return 0;
            }
            node = next;
        }
        return node.pass; // 有几个单词经过 就有几个
    }

    public void traversal(Node node) {
    }

    public static class Node {
        public int pass;
        public int end;
        // 如果想有序，可以使用TreeMap：字符 -> nextNode
        public HashMap<Character, Node> nextMap;

        public Node() {
            pass = 0;
            end = 0;
            nextMap = new HashMap<>();
        }
    }

    public static void main(String[] args) {
        TrieTree tree = new TrieTree();
        tree.insert("美国");
        tree.insert("日本");
        tree.insert("澳大利亚");
        tree.insert("澳洲");
        tree.insert("澳门");
        tree.insert("澳大利亚");
        tree.delete("澳大利亚");
        System.out.println(tree.search("澳大利亚"));
    }
}
