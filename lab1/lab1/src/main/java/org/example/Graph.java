package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import javax.imageio.ImageIO;

public class Graph extends Application{
    private int V;
    private int[][] adjMatrix;
    //去重之后的单词数组
    private String[] words;

    String default_filename="directed_graph.png";



    public Graph(String[] raw_words) {


        //去重
        Set<String> set = new HashSet<>();
        for (String string : raw_words) {
            set.add(string);
        }
        words = new String[set.size()];
        int index = 0;
        for (String string : set) {
            words[index++] = string;
        }
        V = words.length;
        adjMatrix = new int[V][V];

        for(int i=0;i<raw_words.length-1;i++){
            int v=getIndex(words,raw_words[i]);
            int w=getIndex(words,raw_words[i+1]);
            addEdge(v,w);
        }

    }

    public void addEdge(int v, int w) {
        adjMatrix[v][w] += 1;
    }



    public int getIndex(String[] array, String target) {
        // 循环遍历数组
        for (int i = 0; i < array.length; i++) {
            // 如果找到目标元素，则返回索引
            if (array[i].equals(target)) {
                return i;
            }
        }
        // 如果数组中不存在目标元素，则返回-1
        return -1;
    }



    public void start(Stage primaryStage) {
        Pane root = createDirectedGraphPane(); // 调用方法创建 Pane

        Scene scene = new Scene(root, 500, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Directed Graph with Labels");

        // 保存场景为图片
        saveSceneAsImage(root, default_filename);
    }

    // 创建 Pane 来显示有向图
    public Pane createDirectedGraphPane() {
        Pane root = new Pane(); // 使用 Pane
        // 创建顶点
        Circle[] nodes = new Circle[V];
        for (int i = 0; i < V; i++) {
            Circle node = new Circle(30 + i * 100, 150, 20);
            node.setFill(Color.LIGHTBLUE);
            nodes[i] = node;

            Label label = new Label(words[i]);
            label.setLayoutX(20 + i * 100);
            label.setLayoutY(130);
            root.getChildren().addAll(node, label);
        }

        // 添加权重标签和箭头
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                int weight = adjMatrix[i][j];
                if (weight > 0) {
                    double startX = nodes[i].getCenterX();
                    double startY = nodes[i].getCenterY();
                    double endX = nodes[j].getCenterX();
                    double endY = nodes[j].getCenterY();

                    double controlX = (startX + endX) / 2;
                    double controlY = (startY + endY) / 2;
                    controlX += (startY - endY) / 2;
                    controlY += (endX - startX) / 2;

                    QuadCurve curve = new QuadCurve(startX, startY, controlX, controlY, endX, endY);
                    curve.setStroke(Color.BLACK);
                    curve.setFill(null);

                    // 计算曲线的中点
                    double t = 0.5; // 二次贝塞尔曲线参数 t 在 0 和 1 之间
                    double midX = (1 - t) * (1 - t) * startX + 2 * (1 - t) * t * controlX + t * t * endX;
                    double midY = (1 - t) * (1 - t) * startY + 2 * (1 - t) * t * controlY + t * t * endY;

                    // 计算箭头位置和方向
                    double arrowAngle = Math.atan2((endY - controlY), (endX - controlX));
                    double arrowLength = 10;
                    double arrowEndX = endX - arrowLength * Math.cos(arrowAngle);
                    double arrowEndY = endY - arrowLength * Math.sin(arrowAngle);

                    // 添加箭头
                    Polygon arrowHead = new Polygon(
                            endX, endY,
                            arrowEndX - 5 * Math.cos(arrowAngle - Math.PI / 6), arrowEndY - 5 * Math.sin(arrowAngle - Math.PI / 6),
                            arrowEndX - 5 * Math.cos(arrowAngle + Math.PI / 6), arrowEndY - 5 * Math.sin(arrowAngle + Math.PI / 6)
                    );
                    arrowHead.setFill(Color.BLACK);

                    // 添加权重标签
                    Label weightLabel = new Label(String.valueOf(weight));
                    weightLabel.setLayoutX(midX - weightLabel.prefWidth(-1) / 2); // 标签宽度的一半向左移动
                    weightLabel.setLayoutY(midY - weightLabel.prefHeight(-1) / 2); // 标签高度的一半向上移动
                    weightLabel.setFont(Font.font(14));

                    root.getChildren().addAll(curve, arrowHead, weightLabel);
                }
            }
        }

        return root; // 返回 Pane
    }


    private void saveSceneAsImage(Pane pane, String filename) {
        WritableImage image = pane.snapshot(new SnapshotParameters(), null);

        // 将图片保存到文件
        File file = new File(filename);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            System.out.println("图像已保存为 " + filename);
        } catch (IOException e) {
            System.out.println("保存图像时出错：" + e.getMessage());
        }
    }

    public void save(String path){
        Path sourcePath=Paths.get(default_filename);
        Path targetPath=Paths.get(path,default_filename);

        try {
            // 复制文件
            Files.copy(sourcePath, targetPath);
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while copying the file.");
            e.printStackTrace();
        }
    }
    }


