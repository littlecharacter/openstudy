package com.lc.structure.graph.as;

import com.lc.structure.graph.ss.Graph;

import java.util.*;

/**
 * K算法：最小生成树算法
 *
 * @author gujixian
 * @since 2023/7/12
 */
public class Kruskal {
    // Union-Find Set
    public static class UnionFind {
        // key 某一个节点， value key节点往上的节点
        private Map<Graph.Node, Graph.Node> fatherMap;
        // key 某一个集合的代表节点, value key所在集合的节点个数
        private Map<Graph.Node, Integer> sizeMap;

        public UnionFind() {
            fatherMap = new HashMap<Graph.Node, Graph.Node>();
            sizeMap = new HashMap<Graph.Node, Integer>();
        }

        public void makeSets(Collection<Graph.Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Graph.Node node : nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Graph.Node findFather(Graph.Node n) {
            Stack<Graph.Node> path = new Stack<>();
            while (n != fatherMap.get(n)) {
                path.add(n);
                n = fatherMap.get(n);
            }
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), n);
            }
            return n;
        }

        public boolean isSameSet(Graph.Node a, Graph.Node b) {
            return findFather(a) == findFather(b);
        }

        public void union(Graph.Node a, Graph.Node b) {
            if (a == null || b == null) {
                return;
            }
            Graph.Node aDai = findFather(a);
            Graph.Node bDai = findFather(b);
            if (aDai != bDai) {
                int aSetSize = sizeMap.get(aDai);
                int bSetSize = sizeMap.get(bDai);
                if (aSetSize <= bSetSize) {
                    fatherMap.put(aDai, bDai);
                    sizeMap.put(bDai, aSetSize + bSetSize);
                    sizeMap.remove(aDai);
                } else {
                    fatherMap.put(bDai, aDai);
                    sizeMap.put(aDai, aSetSize + bSetSize);
                    sizeMap.remove(bDai);
                }
            }
        }
    }

    public static class EdgeComparator implements Comparator<Graph.Edge> {
        @Override
        public int compare(Graph.Edge o1, Graph.Edge o2) {
            return o1.weight - o2.weight;
        }

    }

    public static Set<Graph.Edge> kruskalMST(Graph graph) {
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodeMap.values());
        // 从小的边到大的边，依次弹出，小根堆！
        PriorityQueue<Graph.Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for (Graph.Edge edge : graph.edgeSet) { // M 条边
            priorityQueue.add(edge);  // O(logM)
        }
        Set<Graph.Edge> result = new HashSet<>();
        while (!priorityQueue.isEmpty()) { // M 条边
            Graph.Edge edge = priorityQueue.poll(); // O(logM)
            if (!unionFind.isSameSet(edge.from, edge.to)) { // O(1)
                result.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }
}
