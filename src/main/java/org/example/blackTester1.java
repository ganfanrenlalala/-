package org.example;

import java.util.Arrays;

public class blackTester1 {
    /*public String queryBridgeWordsPre(String word1, String word2) {
        int index3_1 = myGraph.getIndex(myGraph.getWords(), word1);
        int index3_2 = myGraph.getIndex(myGraph.getWords(), word2);
        int[][] adjMatrix = myGraph.getAdjMatrix();//邻接矩阵
        if (index3_1 == -1 && index3_2 == -1) {
            return "NO1";
        } else if (index3_1 == -1) {
            return "NO2";
        } else if (index3_2 == -1) {
            return "NO3";
        } else {
            int V3 = myGraph.getV();// 去重后单词的个数
            int[] recordBridgeWordIndex = new int[V3];
            int indexForRecord = 0;
            Arrays.fill(recordBridgeWordIndex, -2);
            for (int i = 0; i < V3; i++) {
                if (adjMatrix[index3_1][i] > 0 && adjMatrix[i][index3_2] > 0) {
                    //处理索引i对应的词，如果i对应的词与index3_2的adjMatrix项邻接（val=1），则存在桥接词
                    recordBridgeWordIndex[indexForRecord] = i;
                    indexForRecord++;
                }
            }
            if (indexForRecord == 0) {
                return "NO5";
            }
            //把所有的桥接词当成用空格划分的String进行返回
            StringBuilder ret3_4 = new StringBuilder();
            int count0 = 0;
            for (int i : recordBridgeWordIndex) {
                if (i == -2) {
                    break;
                }
                if (count0 == 0){
                    ret3_4.append(myGraph.getWords()[i]);
                } else {
                    ret3_4.append(" " + myGraph.getWords()[i]);
                }
                count0 ++;
            }
            //System.out.println(count0);
            return ret3_4.toString();
        }
    }*/
}
