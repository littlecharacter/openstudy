package com.lc.structure.graph.as;

import com.lc.structure.graph.ss.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 狄克斯特拉算法：最短路径问题（不限图结构）
 *
 * 题目：LC0743NetworkDelayTime
 *
 * @author gujixian
 * @since 2023/7/12
 */
public class Dijkstra {
    public static Map<Graph.Node, Integer> dijkstra(Graph.Node from) {
        Map<Graph.Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        // 打过对号的点
        Set<Graph.Node> selectedNodes = new HashSet<>();
        Graph.Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            //  原始点  ->  minNode(跳转点)   最小距离distance
            int distance = distanceMap.get(minNode);
            for (Graph.Edge edge : minNode.edges) {
                Graph.Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                } else { // toNode 
                    distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    public static Graph.Node getMinDistanceAndUnselectedNode(Map<Graph.Node, Integer> distanceMap, Set<Graph.Node> touchedNodes) {
        Graph.Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Graph.Node, Integer> entry : distanceMap.entrySet()) {
            Graph.Node node = entry.getKey();
            int distance = entry.getValue();
            if (!touchedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

    public static class NodeRecord {
        public Graph.Node node;
        public int distance;

        public NodeRecord(Graph.Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        private Graph.Node[] nodes; // 实际的堆结构
        // key 某一个node， value 上面堆中的位置
        private HashMap<Graph.Node, Integer> heapIndexMap;
        // key 某一个节点， value 从源节点出发到该节点的目前最小距离
        private HashMap<Graph.Node, Integer> distanceMap;
        private int size; // 堆上有多少个点

        public NodeHeap(int size) {
            nodes = new Graph.Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
        // 判断要不要更新，如果需要的话，就更新
        public void addOrUpdateOrIgnore(Graph.Node node, int distance) {
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(size++);
            }
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            // free C++同学还要把原本堆顶节点析构，对java同学不必
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        private void insertHeapify(int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        private boolean isEntered(Graph.Node node) {
            return heapIndexMap.containsKey(node);
        }

        private boolean inHeap(Graph.Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Graph.Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }

    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Graph.Node, Integer> dijkstraEffective(Graph.Node head, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Graph.Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Graph.Node cur = record.node;
            int distance = record.distance;
            for (Graph.Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }
}
