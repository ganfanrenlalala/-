package org.example;

import java.util.Arrays;

public class DrawerExample1 {

    public String queryBridgeWords(String word1, String word2) {
        String text = "D:\\乱七八糟的作业\\软件工程\\实验\\lab3\\lab1_tester3.txt";

        MyString myString = new MyString(text);
        Graph myGraph0 = new Graph(myString.word_array);

        int index3_1 = myGraph0.getIndex(myGraph0.getWords(), word1);
        int index3_2 = myGraph0.getIndex(myGraph0.getWords(), word2);

        int[][] adjMatrix = myGraph0.getAdjMatrix();//邻接矩阵
        if (index3_1 == -1 && index3_2 == -1) {
            return "NO1";
        } else if (index3_1 == -1) {
            return "NO2";
        } else if (index3_2 == -1) {
            return "NO3";
        } else {
            int V3 = myGraph0.getV();// 去重后单词的个数
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
                    ret3_4.append(myGraph0.getWords()[i]);
                } else {
                    ret3_4.append(" " + myGraph0.getWords()[i]);
                }
                count0 ++;
            }
            //System.out.println(count0);
            return ret3_4.toString();
        }
    }
}
