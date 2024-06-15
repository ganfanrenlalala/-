package org.example;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class MyString {
    String raw_str= "";

    String[] word_array;

    public MyString(String path){
        try {
            // 使用 Paths.get() 方法创建 Path 对象
            List<String> lines = Files.readAllLines(Paths.get(path));

            // 逐行读取文件内容
            for (String line : lines) {
                raw_str=raw_str.concat(" ").concat(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        raw_str = raw_str.toLowerCase();

        char[] charArray=raw_str.toCharArray();
        for (int i = 0; i < raw_str.length(); i++) {
            char ch = charArray[i];
            if ((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')){
                charArray[i]=ch;
            }else {
                charArray[i]=' ';
            }
        }

        int point=0;
        while (charArray[point]==' '){
            if(charArray[point]!=' '){
                break;
            } else {
                point++;
            }
        }
        word_array=(new String(Arrays.copyOfRange(charArray, point, charArray.length))).split("\\s+");

    }
}
