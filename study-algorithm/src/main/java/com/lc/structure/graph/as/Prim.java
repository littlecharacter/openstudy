package com.lc.structure.graph.as;

import com.lc.structure.graph.ss.Graph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * P算法：最小生成树算法
 *
 * @author gujixian
 * @since 2023/7/12
 */
public class Prim {
    public static class EdgeComparator implements Comparator<Graph.Edge> {
        @Override
        public int compare(Graph.Edge o1, Graph.Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Graph.Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆
        PriorityQueue<Graph.Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());

        // 哪些点被解锁出来了
        Set<Graph.Node> nodeSet = new HashSet<>();


        Set<Graph.Edge> result = new HashSet<>(); // 依次挑选的的边在result里

        for (Graph.Node node : graph.nodeMap.values()) { // 随便挑了一个点
            // node 是开始点
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (Graph.Edge edge : node.edges) { // 由一个点，解锁所有相连的边
                    priorityQueue.add(edge);
                }
                while (!priorityQueue.isEmpty()) {
                    Graph.Edge edge = priorityQueue.poll(); // 弹出解锁的边中，最小的边
                    Graph.Node toNode = edge.to; // 可能的一个新的点
                    if (!nodeSet.contains(toNode)) { // 不含有的时候，就是新的点
                        nodeSet.add(toNode);
                        result.add(edge);
                        for (Graph.Edge nextEdge : toNode.edges) {
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
            // break;
        }
        return result;
    }

    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {
        int size = graph.length;
        int[] distances = new int[size];
        boolean[] visit = new boolean[size];
        visit[0] = true;
        for (int i = 0; i < size; i++) {
            distances[i] = graph[0][i];
        }
        int sum = 0;
        for (int i = 1; i < size; i++) {
            int minPath = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] < minPath) {
                    minPath = distances[j];
                    minIndex = j;
                }
            }
            if (minIndex == -1) {
                return sum;
            }
            visit[minIndex] = true;
            sum += minPath;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] > graph[minIndex][j]) {
                    distances[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("hello world!");
    }
}
