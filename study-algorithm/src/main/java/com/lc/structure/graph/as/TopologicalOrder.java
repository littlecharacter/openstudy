package com.lc.structure.graph.as;

import java.util.*;

/**
 * @author gujixian
 * @since 2023/7/12
 */
public class TopologicalOrder {
    public List<Integer> topologicalSort(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null) {
            return result;
        }
        // 构建图
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int[] arr : matrix) {
            int from = arr[1];
            int to = arr[0];
            Set<Integer> edges = graph.getOrDefault(from, new HashSet<>());
            edges.add(to);
            graph.put(from, edges);
            inMap.put(from, Optional.ofNullable(inMap.get(from)).orElse(0));
            inMap.put(to, Optional.ofNullable(inMap.get(to)).orElse(0) + 1);
        }
        // 构建拓扑序
        Queue<Integer> zeroInQueue = new LinkedList<>();
        inMap.forEach((node, in) -> {
            if (Objects.equals(in, 0)) {
                zeroInQueue.offer(node);
            }
        });
        while (!zeroInQueue.isEmpty()) {
            Integer node = zeroInQueue.poll();
            result.add(node);
            Set<Integer> edges = graph.getOrDefault(node, new HashSet<>());
            graph.remove(node);
            for (Integer next : edges) {
                inMap.put(next, inMap.get(next) - 1);
                if (Objects.equals(inMap.get(next), 0)) {
                    zeroInQueue.offer(next);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new TopologicalOrder().topologicalSort(new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}));
    }
}
