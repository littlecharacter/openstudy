package com.lc.algorithm.loadbalance;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Nginx加权轮询算法的Java实现 - 基于 堆和Map（性能高）
 * @author gujixian
 * @since 2023/11/11
 */
public class NC0001WeightedRoundRobin {
    private final PriorityQueue<Server> serverHeap = new PriorityQueue<>((a, b) -> b.currentWeight - a.currentWeight);
    // private final List<Server> serverList = new LinkedList<>();
    private final Map<String, Server> serverMap = new HashMap<>(); // 方便应对增、删、改、查
    private int totalWeight = 0;

    public void addServer(String ip, int weight) {
        Server server = new Server(ip, weight);
        server.setCurrentWeight(weight);
        serverHeap.add(server);
        // serverList.add(server);
        serverMap.put(ip, server);
        totalWeight += weight;
    }

    public String getServer() {
        if (serverHeap.isEmpty()) {
            return null;
        }

        Server maxServer = serverHeap.poll();
        maxServer.currentWeight -= 1;
        serverHeap.add(maxServer);

        if (--totalWeight == 0) {
            serverHeap.clear();
            // for (Server server : serverList) {
            //     server.currentWeight = server.weight;
            //     totalWeight += server.weight;
            //     serverHeap.offer(server);
            // }
            serverMap.forEach((ip, server) -> {
                server.currentWeight = server.weight;
                totalWeight += server.weight;
                serverHeap.offer(server);
            });
        }

        return maxServer.ip;
    }

    public static void main(String[] args) throws Exception {
        NC0001WeightedRoundRobin wrr = new NC0001WeightedRoundRobin();
        wrr.addServer("192.168.1.1", 1);
        wrr.addServer("192.168.1.2", 1);
        wrr.addServer("192.168.1.3", 10);

        for (int i = 0; i < 120; i++) {
            System.out.println(wrr.getServer());
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static class Server {
        private String ip;
        private int weight;
        private int currentWeight;

        public Server(String ip, int weight) {
            this.ip = ip;
            this.weight = weight;
            this.currentWeight = weight;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getCurrentWeight() {
            return currentWeight;
        }

        public void setCurrentWeight(int currentWeight) {
            this.currentWeight = currentWeight;
        }
    }
}
