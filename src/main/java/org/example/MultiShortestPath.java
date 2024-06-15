package org.example;

import java.util.ArrayList;


public class MultiShortestPath {
    public int shortestPathNumbers = 0;
    public void setShortestPathNumbers(int shortestPathNumbers){
        this.shortestPathNumbers = shortestPathNumbers;
    }
    public ArrayList<ArrayList<Integer>> findMultiPath(int[][] adjMatrix, int startNode, int endNode){
        ArrayList<ArrayList<Integer>> MultiShortestPaths = new ArrayList<>();


        ArrayList<ArrayList<Integer>> allPaths = findAllPaths(adjMatrix, startNode, endNode);
        if (allPaths.isEmpty()) {
            System.out.println("No path exists");
        } else {
            System.out.println("All paths between node " + startNode + " and node " + endNode + ":");

            // Sort paths by length
            allPaths.sort((a, b) -> Integer.compare(a.size(), b.size()));

            int shortestLength = allPaths.get(0).size() - 1;
            System.out.println("Shortest paths (length " + shortestLength + "):");
            for (ArrayList<Integer> path : allPaths) {
                if (path.size() - 1 == shortestLength) {
                    System.out.println("Path: " + path + ", Length: " + (path.size() - 1));
                    //assert false;
                    MultiShortestPaths.add(path);
                    shortestPathNumbers ++;
                } else {
                    break; // All shortest paths found
                }
            }
        }
        return MultiShortestPaths;
    }
    public static ArrayList<ArrayList<Integer>> findAllPaths(int[][] adjMatrix, int startNode, int endNode) {
        ArrayList<ArrayList<Integer>> allPaths = new ArrayList<>();
        ArrayList<Integer> currentPath = new ArrayList<>();
        boolean[] visited = new boolean[adjMatrix.length];

        currentPath.add(startNode);
        dfs(adjMatrix, startNode, endNode, visited, currentPath, allPaths);

        for (ArrayList<Integer> item0 : allPaths){
            System.out.println("所有的路径有:" + item0);
        }

        return allPaths;
    }

    private static void dfs(int[][] adjMatrix, int currentNode, int endNode, boolean[] visited, ArrayList<Integer> currentPath, ArrayList<ArrayList<Integer>> allPaths) {
        visited[currentNode] = true;

        if (currentNode == endNode) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            for (int i = 0; i < adjMatrix.length; i++) {
                if (adjMatrix[currentNode][i] == 1 && !visited[i]) {
                    currentPath.add(i);
                    dfs(adjMatrix, i, endNode, visited, currentPath, allPaths);
                    currentPath.remove(currentPath.size() - 1);
                }
            }
        }

        visited[currentNode] = false;
    }

    public int getShortestPathNumbers(){
        return shortestPathNumbers;
    }
}
