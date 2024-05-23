package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalk {
    Graph myGraph;
    String[] words;

    public RandomWalk(Graph myGraph, String[] words){
        this.myGraph = myGraph;
        this.words = words;//去重后的单词
    }
    //计算路径长度
    public int lengthRoute = 0;

    //在给定的图的基础上，计算边数，并统计边数的出现次数
    //第六问,先得到一条具体的random walk路径
    public List<Integer> randomWalkResult() {
        int [][] edgeAppearMatrix = new int[words.length][words.length];

        List<Integer> arrayForRoute = new ArrayList<Integer>();

        //开始节点、开始节点的索引的计算
        String startNode = randomWalk();
        int startNodeIndex = myGraph.getIndex(words, startNode);
        //arrayForRoute.add(startNodeIndex);
        int curNode = startNodeIndex;

        while(true){
            int noZeroCount = 0;
            for (int i = 0; i<words.length; i++) {
                if (myGraph.getAdjMatrix()[curNode][i] > 0) {
                    noZeroCount ++;
                }
            }
            System.out.println("这一行有" + noZeroCount + "个非零值，从中选择");
            if (noZeroCount == 0){
                arrayForRoute.add(curNode);
                System.out.println("由终点结束的路径是" + arrayForRoute);
                break;
            }
            arrayForRoute.add(curNode);
            lengthRoute ++;
            int selectedEdgeIndex = randomEdgeResult(noZeroCount) + 1;
            int selectedEdge = 0;
            for (int i = 0; i< words.length; i++){
                if (myGraph.getAdjMatrix()[curNode][i]>0){
                    selectedEdgeIndex --;
                }
                if (selectedEdgeIndex == 0){
                    break;
                }
                selectedEdge ++;
            }
            edgeAppearMatrix[curNode][selectedEdge] ++;
            //arrayForRoute.add(selectedEdge);
            if (edgeAppearMatrix[curNode][selectedEdge] > 1){
                arrayForRoute.add(selectedEdge);
                System.out.println("由于重边终止的路径是" + arrayForRoute);
                //return arrayForRoute.toString();
                break;
            }
            curNode = selectedEdge;//更新当前的节点
        }
        return arrayForRoute;
    }
    //返回随机的开始节点
    public String randomWalk() {
        // 指定范围的上限和下限
        int min = 0;
        int max = words.length - 1;

        // 创建一个 Random 对象
        Random random = new Random();

        // 生成指定范围内的随机整数
        int randomNumber = random.nextInt(max - min + 1) + min;
        String startWord = words[randomNumber];
        return startWord;
    }
    //返回随即结果
    public int randomEdgeResult(int noZeroCount) {
        int min = 0;
        int max = noZeroCount - 1;
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        return randomNumber;//从0开始的
    }
    //返回随机游走的路径长度
    public int getLengthRoute() {
        return lengthRoute;
    }
}
