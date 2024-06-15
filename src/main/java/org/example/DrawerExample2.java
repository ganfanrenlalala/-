package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrawerExample2 {
    public void testrandomwalk(String text){

        MyString myString = new MyString(text);
        Graph myGraph0 = new Graph(myString.word_array);

        //去重
        Set<String> set = new HashSet<>();
        for (String string : myString.word_array) {
            set.add(string);
        }
        String[] words = new String[set.size()];
        int index = 0;
        for (String string : set) {
            words[index++] = string;
        }

        RandomWalk randomWalk=new RandomWalk(myGraph0,words);

    }
}
