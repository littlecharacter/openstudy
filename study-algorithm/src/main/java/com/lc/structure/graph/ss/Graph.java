package com.lc.structure.graph.ss;

import com.alibaba.fastjson.JSON;
import javafx.util.Pair;
import lombok.*;

import java.util.*;

/**
 * @author gujixian
 * @since 2022/11/25
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Graph {
    // 邻接表法一（工程）：点（编号 -> 点结构）、边分开表示
    public Map<Integer, Node> nodeMap = new HashMap<>();
    public Set<Edge> edgeSet = new HashSet<>();
    // 邻接表法二（面试）：原节点编号（整型或字符串，根据题目来） -> 目标节点，权值
    // public Map<Integer, Set<Pair<Integer, Integer>>> graph = new HashMap<>();

    public static Graph buildGraph(Integer[][] matrix) {
        Graph graph = new Graph();
        for (Integer[] m : matrix) {
            // 构造工程图
            Integer from = m[0];
            Integer to = m[1];
            Integer weight = m[2];
            // 处理点
            graph.nodeMap.putIfAbsent(from, new Node(from));
            graph.nodeMap.putIfAbsent(to, new Node(to));
            Node fromNode = graph.nodeMap.get(from);
            Node toNode = graph.nodeMap.get(to);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            // 处理边
            Edge edge = new Edge(weight, fromNode, toNode);
            fromNode.edges.add(edge);
            // toNode.edges.add(edge); // matrix 的一条记录，只能表示一条有向边，这条边只属于 fromNode，不属于toNode
            graph.edgeSet.add(edge);


            // // 构造面试图
            // Set<Pair<Integer, Integer>> edges = graph.graph.getOrDefault(from, new HashSet<>());
            // edges.add(new Pair<>(to, weight));
            // graph.graph.put(from, edges);
        }

        return graph;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Node {
        // 节点编号
        public int code;
        // 节点描述
        public String desc;
        // 节点的值
        public int value;

        // 节点入度
        public int in;
        // 节点出度
        public int out;

        // next 节点集合
        public List<Node> nexts = new ArrayList<>();
        // edge 边集合
        public List<Edge> edges = new ArrayList<>();

        public Node(int code) {
            this.code = code;
        }

        public Node(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Node(int code, String desc, int value) {
            this.code = code;
            this.desc = desc;
            this.value = value;
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Edge {
        public int weight;
        public Node from;
        public Node to;
    }


    public static void main(String[] args) {
        Integer[][] matrix = new Integer[3][3];
        matrix[0][0] = 0;
        matrix[0][1] = 1;
        matrix[0][2] = 3;
        matrix[1][0] = 0;
        matrix[1][1] = 2;
        matrix[1][2] = 3;
        matrix[2][0] = 1;
        matrix[2][1] = 2;
        matrix[2][2] = 5;
        Graph graph = Graph.buildGraph(matrix);
        System.out.println(JSON.toJSONString(graph.nodeMap.get(1)));
        // System.out.println(JSON.toJSONString(graph.graph.get(1)));
        //
        // graph.graph = new HashMap<>();
        // graph.graph.put(0, new HashSet<>(Arrays.asList(new Pair<>(1, 1), new Pair<>(3, 1), new Pair<>(4, 1))));
        // graph.graph.put(1, new HashSet<>(Arrays.asList(new Pair<>(0, 1), new Pair<>(2, 1))));
        // graph.graph.put(2, new HashSet<>(Arrays.asList(new Pair<>(1, 1), new Pair<>(3, 1))));
        // graph.graph.put(3, new HashSet<>(Arrays.asList(new Pair<>(0, 1), new Pair<>(2, 1), new Pair<>(4, 1))));
        // graph.graph.put(4, new HashSet<>(Arrays.asList(new Pair<>(0, 1), new Pair<>(3, 1))));

        print(graph);
    }

    private static void print(Graph graph) {
        // Set<Integer> visitedNode = new HashSet<>();
        // Deque<Integer> stack = new ArrayDeque<>();
        // stack.offerLast(0);
        // visitedNode.add(0);
        // while (!stack.isEmpty()) {
        //     int node = stack.pollLast();
        //     System.out.println(node);
        //     for (Pair<Integer, Integer> edge : graph.graph.get(node)) {
        //         if (!visitedNode.contains(edge.getKey())) {
        //             visitedNode.add(edge.getKey());
        //             stack.offerLast(edge.getKey());
        //         }
        //     }
        // }
    }
}
