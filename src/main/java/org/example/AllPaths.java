package org.example;

import java.util.*;

public class AllPaths {
    int[][] adjMatrix;
    int startNode;
    static Graph myGraph;

    public AllPaths(int[][] adjMatrix, int startNode, Graph myGraph){
        this.adjMatrix = adjMatrix;
        this.startNode = startNode;
        //System.out.println("标号是:" + startNode);
        this.myGraph = myGraph;
    }
    private static final int INF = Integer.MAX_VALUE;

    public String dijkstra(int[][] graph) {
        int V = graph.length;
        int[] dist = new int[V];
        int[] prev = new int[V]; // 用于存储最短路径的前一个节点
        boolean[] visited = new boolean[V];
        Arrays.fill(dist, INF);
        dist[startNode] = 0;

        for (int i = 0; i < V - 1; i++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] == 1 && dist[u] != INF && dist[u] + 1 < dist[v]) {
                    dist[v] = dist[u] + 1;
                    prev[v] = u; // 记录前一个节点
                }
            }
        }

        return printSolution(dist, prev, startNode);
    }

    private static int minDistance(int[] dist, boolean[] visited) {
        int min = INF;
        int minIndex = -1;

        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    private static String printSolution(int[] dist, int[] prev, int start) {
        StringBuilder result = new StringBuilder();
        int V = dist.length;
        result.append("Shortest Path from ").append(myGraph.getWords()[start]).append(" to:\n");
        for (int i = 0; i < V; i++) {
            if (i != start) {
                if (dist[i]> 1000){
                    result.append("   ").append(myGraph.getWords()[start]).append(" -> ").
                            append(myGraph.getWords()[i]).append(" : ").
                            append(dist[i]>1000?"No Path" + dist[i] : dist[i]).
                            append(", ").
                            append("\n");
                }else{
                    result.append("   ").append(myGraph.getWords()[start]).append(" -> ").
                            append(myGraph.getWords()[i]).append(" : ").
                            append(dist[i]>1000?"No Path":dist[i]).
                            append(", Path: ").
                            append(getPath(prev, start, i)).append("\n");
                }

            }
        }
        return result.toString();
    }

    private static String getPath(int[] prev, int start, int end) {
        StringBuilder path = new StringBuilder();
        int at = end;

        while (at != start) {
            path.insert(0, " -> " + myGraph.getWords()[at]);
            at = prev[at];
        }
        path.insert(0, myGraph.getWords()[start]);
        return path.toString();
    }
}
