package org.example;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;


public class DrawerExample extends Application {

    BorderPane root;
    StackPane[] pages;

    MyString myString;
    public static Graph myGraph;

    //第六问
    private Timer timer;
    private Label outputLabel;
    private int currentIndex = 0;

    //第一问myGraph生成优化
    Label label_in_1;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();

        // Create six pages
        pages = new StackPane[6];
        for (int i = 0; i < 6; i++) {
            Label label=new Label("Function " + (i + 1));
            pages[i] = new StackPane(label);
            label.setVisible(false);
        }
        for (int i = 0; i < 6; i++){
            setPages(i);
        }

        // Set the default page
        root.setCenter(pages[0]);

        // Create a button list
        ListView<String> menuList = new ListView<>();
        menuList.getItems().addAll("Function 1", "Function 2", "Function 3", "Function 4", "Function 5", "Function 6");
        menuList.setOnMouseClicked(event -> {
            String selectedPage = menuList.getSelectionModel().getSelectedItem();
            switchPage(selectedPage);
        });

        // Put the button list in the drawer
        VBox drawerContent = new VBox();
        drawerContent.getChildren().addAll(new Label("Functions:"), menuList);

        ScrollPane drawerPane = new ScrollPane(drawerContent);
        drawerPane.setFitToWidth(true);
        drawerPane.setPadding(new Insets(10));

        // Create the drawer
        root.setLeft(drawerPane);

        // Bind font size of labels to scene width and height
        for (StackPane page : pages) {
            Label label = (Label) page.getChildren().get(0);
            label.styleProperty().bind(Bindings.concat("-fx-font-size: ", primaryStage.widthProperty().divide(30), ";"));
        }

        // Bind font size of labels in drawer to scene width and height
        Label drawerLabel = (Label) drawerContent.getChildren().get(0);
        drawerLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", primaryStage.widthProperty().divide(50), ";"));

        // Bind font size of list items to scene width and height
        menuList.styleProperty().bind(Bindings.concat("-fx-font-size: ", primaryStage.widthProperty().divide(50), ";"));
        menuList.setFixedCellSize(primaryStage.widthProperty().divide(50).doubleValue() + 5); // Adjust cell height

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("LAB1_ycj&fym");

        // Set the stage to full screen
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    // Switch pages
    private void switchPage(String pageName) {
        switch (pageName) {
            case "Function 1":
                root.setCenter(pages[0]);
                break;
            case "Function 2":
                root.setCenter(pages[1]);
                break;
            case "Function 3":
                root.setCenter(pages[2]);
                break;
            case "Function 4":
                root.setCenter(pages[3]);
                break;
            case "Function 5":
                root.setCenter(pages[4]);
                break;
            case "Function 6":
                root.setCenter(pages[5]);
                break;
            default:
                System.out.println("Invalid page name");
        }
    }

    // 为桥接词返回值创建方便的函数,第三问
    public String queryBridgeWordsPre(String word1, String word2) {
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
    }
    // 桥接词的查询,第三问
    public String queryBridgeWords(String word1, String word2) {
        //如果单词不存在，则返回index=-1
        String ret_pre3 = queryBridgeWordsPre(word1, word2);
        System.out.println("第三问返回值1:" + ret_pre3);
        switch (ret_pre3) {
            case "NO1" -> {
                String ret3_1 = "No \"" + word1 + "\" and \"" + word2 + "\" in the graph!";
                return ret3_1;
            }
            case "NO2" -> {
                String ret3_2 = "No \"" + word1 + "\" in the graph!";
                return ret3_2;
            }
            case "NO3" -> {
                String ret3_3 = "No \"" + word2 + "\" in the graph!";
                return ret3_3;
            }
            case "NO5" -> {
                String ret3_5 = "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
                return ret3_5;
            }
            default -> {
                boolean containsSpace = false;
                for (int i = 0; i < ret_pre3.length(); i++) {
                    if (ret_pre3.charAt(i) == ' ') {
                        containsSpace = true;
                        break;
                    }
                }
                if (containsSpace == true){
                    String[] words_pre3 = ret_pre3.split(" ");
                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < words_pre3.length; i++) {
                        if (i < words_pre3.length - 2) {
                            result.append(words_pre3[i]).append(", ");
                        } else if (i == words_pre3.length - 2) {
                            result.append(words_pre3[i]).append(" and ");
                        } else {
                            result.append(words_pre3[i]);
                        }
                    }
                    ret_pre3 = result.toString();
                    System.out.println(ret_pre3);
                }

                return "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" is:" + ret_pre3;  // 正确的情况，开始查找：1.存在桥接词2.不存在
            }
        }
    }

    //第四问
    public String generateNewText(String inputText) {
        String[] words = inputText.split(" "); // 使用空格分隔字符串
        String copiedString = "";
        String newSentence = inputText.substring(0);
        int insertCount = 0;
        for (int i = 0; i < words.length - 1; i++){
            String newWord = queryBridgeWordsPre(words[i], words[i + 1]);
            String[] newWords = newWord.split(" ");
            //System.out.println("newWords 长度" + newWords.length);
            if (newWords.length >= 2) {
                // 指定范围的上限和下限
                int min = 0;
                int max = newWords.length - 1;

                // 创建一个 Random 对象
                Random random = new Random();

                // 生成指定范围内的随机整数
                int randomNumber = random.nextInt(max - min + 1) + min;
                newWord = newWords[randomNumber];
            }
            System.out.println("new word is:" + newWord);
            if (newWord.equals("NO1") || newWord.equals("NO2") || newWord.equals("NO3") || newWord.equals("NO5")) {
                copiedString = newSentence;
                continue;
            } else {
                //把newWord插入新的句子,在原始句子的第i+1个单词后面添加
                int index = i + 1 + insertCount;
                newSentence = insertWord(newSentence, newWord, index);
                insertCount ++ ;
                copiedString = newSentence.substring(0);
            }
        }
        return copiedString;
    }
    //第四问
    public static String insertWord(String originalSentence, String newWord, int index) {
        // 使用空格分隔句子中的单词
        String[] words = originalSentence.split(" ");
        StringBuilder newSentence = new StringBuilder();

        // 遍历单词数组，将新单词插入到指定位置后面
        for (int i = 0; i < words.length; i++) {
            newSentence.append(words[i]);
            if (i == index - 1) { // 在指定单词的后面插入新单词
                newSentence.append(" ").append(newWord);
            }
            if (i < words.length - 1) { // 如果不是最后一个单词，则添加空格
                newSentence.append(" ");
            }
        }

        return newSentence.toString();
    }

    //第五问
    public String calcShortestPath(String word1, String word2){
        int[][] adjMatrix = myGraph.getAdjMatrix();
        int start5 = myGraph.getIndex(myGraph.getWords(), word1);
        int end5 = myGraph.getIndex(myGraph.getWords(), word2);
        System.out.println("start5 和end5是:" + start5 + " " + end5);
        String str5 = shortestPath(adjMatrix, start5, end5).toString();
        System.out.println(str5);
        return str5;
    }
    //第五问调用的函数
    public static String shortestPath(int[][] adjMatrix, int start, int end) {
        final int INF = Integer.MAX_VALUE;
        int V = adjMatrix.length;
        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(v -> dist[v]));
        pq.offer(start);

        int[] prev = new int[V];
        Arrays.fill(prev, -1);

        while (!pq.isEmpty()) {
            int u = pq.poll();
            for (int v = 0; v < V; v++) {
                if (adjMatrix[u][v] != 0) {
                    int alt = dist[u] + adjMatrix[u][v];
                    if (alt < dist[v]) {
                        dist[v] = alt;
                        prev[v] = u;
                        pq.offer(v);
                    }
                }
            }
        }

        if (dist[end] == INF) {
            return "No path exists";
        }

        StringBuilder path = new StringBuilder();
        path.append(myGraph.getWords()[end]);
        int curr = end;
        while (prev[curr] != -1) {
            path.insert(0, "->");
            path.insert(0, myGraph.getWords()[prev[curr]]);
            curr = prev[curr];
        }
        return path.toString() + ", the length is:" + dist[end];
    }

    //第六问：打印函数：
    private void startPrinting() {
        RandomWalk randomWalk = new RandomWalk(myGraph, myGraph.getWords());
        List<Integer> ret6_1 = randomWalk.randomWalkResult();
        System.out.println("返回的路径是" + ret6_1);
        int lengthOfRet6_1 = ret6_1.size();

        System.out.println("长度:" + lengthOfRet6_1);
        String[] newPath = new String[lengthOfRet6_1];
        for (int item0 = 0; item0< lengthOfRet6_1; item0++){
            newPath[item0] = myGraph.getWords()[ret6_1.get(item0)];
        }
        System.out.println(Arrays.toString(newPath));//newPath属于随机游走的已经生成好的路径，让用户随时选择打印多长就可以了
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (currentIndex < newPath.length) {
                        String currentElement = newPath[currentIndex];
                        Platform.runLater(() -> {
                            String existingText = outputLabel.getText();
                            outputLabel.setText(existingText + "\n" + currentElement);
                        });
                        currentIndex++;
                    } else {
                        stopPrinting();
                    }
                }
            }, 0, 3000);
        }
    }

    private void stopPrinting() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            Platform.runLater(() -> {
                String finalOutput = outputLabel.getText() + "\nPrinting stopped. Total elements printed: " + currentIndex;
                outputLabel.setText(finalOutput);
                saveToFile(finalOutput);
            });
        }
    }

    private void saveToFile(String content) {
        try (FileWriter writer = new FileWriter("output6.txt")) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //第一问的myGraph生成进行优化，避免作用域错误
    private void showDirectedGraph(TextField textField1){
        // 获取输入框中的文本
        String text = textField1.getText();
        myString = new MyString(text);
        // 创建一个新的标签，显示按钮点击后的文本
        label_in_1 = new Label(Arrays.toString(myString.word_array));
        // 将标签添加到根节点中
        pages[0].getChildren().add(label_in_1);
        myGraph = new Graph(myString.word_array);
    }

    public void setPages(int i){
        switch (i){
            case 0:
                // 创建输入框
                TextField textField1 = new TextField();
                // 设置输入框的最小宽度和高度
                textField1.setMinWidth(200);
                textField1.setMinHeight(30);
                // 绑定输入框的宽度和高度到根节点的宽度和高度
                textField1.prefWidthProperty().bind(pages[0].widthProperty().multiply(0.5));
                textField1.prefHeightProperty().bind(pages[0].heightProperty().multiply(0.1));

                // 创建按钮
                Button button1 = new Button("Input completed");
                // 设置按钮的最小宽度和高度
                button1.setMinWidth(100);
                button1.setMinHeight(50);
                // 绑定按钮的宽度和高度到根节点的宽度和高度
                button1.prefWidthProperty().bind(pages[0].widthProperty().multiply(0.3));
                button1.prefHeightProperty().bind(pages[0].heightProperty().multiply(0.2));

                // 当按钮被点击时触发事件
                button1.setOnAction(event -> showDirectedGraph(textField1));
                /**button1.setOnAction(event -> {
                    // 获取输入框中的文本
                    String text = textField1.getText();
                    myString=new MyString(text);
                    // 创建一个新的标签，显示按钮点击后的文本
                    Label label = new Label(Arrays.toString(myString.word_array));
                    // 将标签添加到根节点中
                    pages[0].getChildren().add(label);
                    myGraph=new Graph(myString.word_array);
                });*/

                // 创建垂直布局
                VBox vbox1 = new VBox(10); // 设置垂直间距为10
                vbox1.getChildren().addAll(textField1, button1);
                vbox1.setPadding(new Insets(20)); // 设置内边距

                // 将垂直布局添加到pages[0]的左上角
                StackPane.setAlignment(vbox1, Pos.TOP_LEFT);
                pages[0].getChildren().add(vbox1);
                break;

            case 1:
                // 创建输入框
                TextField textField2 = new TextField();
                // 设置输入框的最小宽度和高度
                textField2.setMinWidth(200);
                textField2.setMinHeight(30);
                // 绑定输入框的宽度和高度到根节点的宽度和高度
                textField2.prefWidthProperty().bind(pages[1].widthProperty().multiply(0.5));
                textField2.prefHeightProperty().bind(pages[1].heightProperty().multiply(0.1));

                // 创建按钮
                Button button2_1 = new Button("Show!");
                // 设置按钮的最小宽度和高度
                button2_1.setMinWidth(100);
                button2_1.setMinHeight(50);
                // 绑定按钮的宽度和高度到根节点的宽度和高度
                button2_1.prefWidthProperty().bind(pages[1].widthProperty().multiply(0.3));
                button2_1.prefHeightProperty().bind(pages[1].heightProperty().multiply(0.2));

                // 创建按钮
                Button button2_2 = new Button("Input completed,save!");
                // 设置按钮的最小宽度和高度
                button2_2.setMinWidth(100);
                button2_2.setMinHeight(50);
                // 绑定按钮的宽度和高度到根节点的宽度和高度
                button2_2.prefWidthProperty().bind(pages[1].widthProperty().multiply(0.3));
                button2_2.prefHeightProperty().bind(pages[1].heightProperty().multiply(0.2));

                // 创建垂直布局
                VBox vbox2 = new VBox(10); // 设置垂直间距为10
                vbox2.getChildren().addAll(button2_1,button2_2,textField2);
                vbox2.setPadding(new Insets(20)); // 设置内边距

                // 将垂直布局添加到pages[1]的左上角
                StackPane.setAlignment(vbox2, Pos.TOP_LEFT);
                pages[1].getChildren().add(vbox2);

                // 当按钮被点击时触发事件
                button2_1.setOnAction(event ->{
                            myGraph.start(new Stage());
                            Pane pane=myGraph.createDirectedGraphPane();
                            ScrollPane scrollPane = new ScrollPane();
                            scrollPane.setContent(pane);
                            // 允许横向和纵向的滚动
                            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                            // 当 ScrollPane 的视口大小发生变化时，更新滚动条的范围
                            scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
                                // 获取 Pane 的边界
                                Bounds contentBounds = pane.getBoundsInParent();

                                // 设置滚动条的范围
                                scrollPane.setHmin(0);
                                scrollPane.setHmax(contentBounds.getWidth());
                                scrollPane.setVmin(0);
                                scrollPane.setVmax(contentBounds.getHeight());
                                // 设置滚动条的位置
                                scrollPane.setHvalue(0);
                                scrollPane.setVvalue(0);
                            });

                            vbox2.getChildren().add(scrollPane);
                        }
                );
                button2_2.setOnAction(event -> {
                    // 获取输入框中的文本
                    String text = textField2.getText();
                    myGraph.save(text);
                });
                break;

            case 2:
                TextField textField3_1 = new TextField();
                TextField textField3_2 = new TextField();
                // 设置输入框的最小宽度和高度
                textField3_1.setMinWidth(200);
                textField3_1.setMinHeight(30);
                textField3_2.setMinWidth(200);
                textField3_2.setMinHeight(30);
                // 绑定输入框的宽度和高度到根节点的宽度和高度
                textField3_1.prefWidthProperty().bind(pages[2].widthProperty().multiply(0.5));
                textField3_1.prefHeightProperty().bind(pages[2].heightProperty().multiply(0.1));
                textField3_2.prefWidthProperty().bind(pages[2].widthProperty().multiply(0.5));
                textField3_2.prefHeightProperty().bind(pages[2].heightProperty().multiply(0.1));

                //从textField3_1和textField3_2获取word1和word2准备查询桥接词
                Button button3_1 = new Button("Input3 completed");
                // 设置按钮的最小宽度和高度
                button3_1.setMinWidth(100);
                button3_1.setMinHeight(50);
                // 绑定按钮的宽度和高度到根节点的宽度和高度
                button3_1.prefWidthProperty().bind(pages[2].widthProperty().multiply(0.3));
                button3_1.prefHeightProperty().bind(pages[2].heightProperty().multiply(0.2));

                // 当按钮被点击时触发事件
                button3_1.setOnAction(event -> {
                    // 获取输入框中的文本
                    String text3_1 = textField3_1.getText();
                    //myString = new MyString(text3_1);
                    String text3_2 = textField3_2.getText();

                    String text3_result = queryBridgeWords(text3_1, text3_2);
                    //****调用程序*****//
                    // 创建一个新的标签，显示按钮点击后的文本
                    TextField textField3_ap = new TextField(text3_result);
                    Label label = new Label(text3_result);
                    // 将标签添加到根节点中
                    //pages[2].getChildren().add(label);
                    pages[2].getChildren().add(textField3_ap);
                });
                // 创建垂直布局
                VBox vbox3_1 = new VBox(10); // 设置垂直间距为10
                vbox3_1.getChildren().addAll(textField3_1, textField3_2, button3_1);
                vbox3_1.setPadding(new Insets(20)); // 设置内边距

                // 将垂直布局添加到pages[2]的左上角
                StackPane.setAlignment(vbox3_1, Pos.TOP_LEFT);
                pages[2].getChildren().add(vbox3_1);
                break;

            case 3:
                // 创建输入框
                TextField textField4 = new TextField();
                // 设置输入框的最小宽度和高度
                textField4.setMinWidth(200);
                textField4.setMinHeight(30);
                // 绑定输入框的宽度和高度到根节点的宽度和高度
                textField4.prefWidthProperty().bind(pages[3].widthProperty().multiply(0.5));
                textField4.prefHeightProperty().bind(pages[3].heightProperty().multiply(0.1));

                // 创建按钮
                Button button4 = new Button("Input4 completed");
                // 设置按钮的最小宽度和高度
                button4.setMinWidth(100);
                button4.setMinHeight(50);
                // 绑定按钮的宽度和高度到根节点的宽度和高度
                button4.prefWidthProperty().bind(pages[3].widthProperty().multiply(0.3));
                button4.prefHeightProperty().bind(pages[3].heightProperty().multiply(0.2));

                // 当按钮被点击时触发事件
                button4.setOnAction(event -> {
                    // 获取输入框中的文本
                    String text4 = textField4.getText();
                    //myString=new MyString(text4);
                    //****调用程序*****//
                    String ret4 = generateNewText(text4);
                    // 创建一个新的标签，显示按钮点击后的文本
                    Label label = new Label(ret4);
                    // 将标签添加到根节点中
                    pages[3].getChildren().add(label);
                });
                // 创建垂直布局
                VBox vbox4 = new VBox(10); // 设置垂直间距为10
                vbox4.getChildren().addAll(textField4, button4);
                vbox4.setPadding(new Insets(20)); // 设置内边距

                // 将垂直布局添加到pages[3]的左上角
                StackPane.setAlignment(vbox4, Pos.TOP_LEFT);
                pages[3].getChildren().add(vbox4);
                break;

            case 4:
                TextField textField5_1 = new TextField();
                TextField textField5_2 = new TextField();
                // 设置输入框的最小宽度和高度
                textField5_1.setMinWidth(200);
                textField5_1.setMinHeight(30);
                textField5_2.setMinWidth(200);
                textField5_2.setMinHeight(30);
                // 绑定输入框的宽度和高度到根节点的宽度和高度
                textField5_1.prefWidthProperty().bind(pages[4].widthProperty().multiply(0.5));
                textField5_1.prefHeightProperty().bind(pages[4].heightProperty().multiply(0.1));
                textField5_2.prefWidthProperty().bind(pages[4].widthProperty().multiply(0.5));
                textField5_2.prefHeightProperty().bind(pages[4].heightProperty().multiply(0.1));

                //从textField5_1和textField5_2获取word1和word2准备查询桥接词
                Button button5_1 = new Button("Input5 completed");
                Button button5_2 = new Button("show the graph");
                // 设置按钮的最小宽度和高度
                button5_1.setMinWidth(100);
                button5_1.setMinHeight(50);
                button5_2.setMinWidth(100);
                button5_2.setMinHeight(50);
                // 绑定按钮的宽度和高度到根节点的宽度和高度
                button5_1.prefWidthProperty().bind(pages[4].widthProperty().multiply(0.3));
                button5_1.prefHeightProperty().bind(pages[4].heightProperty().multiply(0.2));
                button5_2.prefWidthProperty().bind(pages[4].widthProperty().multiply(0.3));
                button5_2.prefHeightProperty().bind(pages[4].heightProperty().multiply(0.2));

                // 创建垂直布局
                VBox vbox5_1 = new VBox(10); // 设置垂直间距为10
                vbox5_1.getChildren().addAll(textField5_1, textField5_2, button5_1, button5_2);
                vbox5_1.setPadding(new Insets(20)); // 设置内边距

                // 将垂直布局添加到pages[4]的左上角
                StackPane.setAlignment(vbox5_1, Pos.TOP_LEFT);
                pages[4].getChildren().add(vbox5_1);
                // 当按钮被点击时触发事件
                button5_1.setOnAction(event -> {
                    // 获取输入框中的文本
                    String text5_1 = textField5_1.getText();
                    String text5_2 = textField5_2.getText();

                    String text5_result = calcShortestPath(text5_1, text5_2);
                    //****调用程序*****//
                    // 创建一个新的标签，显示按钮点击后的文本
                    Label label = new Label(text5_result);
                    // 将标签添加到根节点中
                    pages[4].getChildren().add(label);
                });

                button5_2.setOnAction(event -> {
                    // 获取输入框中的文本
                    String text5_1 = textField5_1.getText();
                    String text5_2 = textField5_2.getText();

                    String text5_result = calcShortestPath(text5_1, text5_2);
                    String text5_final = "";
                    if (text5_result.equals("No path exists")) {
                        // stay null
                    } else {
                        int index_comma = text5_result.indexOf(",");
                        text5_final = text5_result.substring(0, index_comma);
                    }

                    myGraph.start_marked(new Stage(), text5_final);

                    Pane markedPane = myGraph.createAnotherGraphPane(text5_final);

                    ScrollPane scrollPane = new ScrollPane();
                    scrollPane.setContent(markedPane);
                    // 允许横向和纵向的滚动
                    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                    // 当 ScrollPane 的视口大小发生变化时，更新滚动条的范围
                    scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
                        // 获取 Pane 的边界
                        Bounds contentBounds = markedPane.getBoundsInParent();

                        // 设置滚动条的范围
                        scrollPane.setHmin(0);
                        scrollPane.setHmax(contentBounds.getWidth());
                        scrollPane.setVmin(0);
                        scrollPane.setVmax(contentBounds.getHeight());
                        // 设置滚动条的位置
                        scrollPane.setHvalue(0);
                        scrollPane.setVvalue(0);
                    });
                    vbox5_1.getChildren().add(scrollPane);
                });
                break;

            case 5:
                //从textField5_1和textField5_2获取word1和word2准备查询桥接词
                Button button6_1 = new Button("Thread start"); //点击按钮，进程开始
                Button button6_2 = new Button("Thread pause, save the result"); //点击按钮，进程结束并保存路径
                // 设置按钮的最小宽度和高度
                button6_1.setMinWidth(100);
                button6_1.setMinHeight(50);
                button6_2.setMinWidth(100);
                button6_2.setMinHeight(50);
                // 绑定按钮的宽度和高度到根节点的宽度和高度
                button6_1.prefWidthProperty().bind(pages[5].widthProperty().multiply(0.3));
                button6_1.prefHeightProperty().bind(pages[5].heightProperty().multiply(0.2));
                button6_2.prefWidthProperty().bind(pages[5].widthProperty().multiply(0.3));
                button6_2.prefHeightProperty().bind(pages[5].heightProperty().multiply(0.2));

                //标签初始化：
                outputLabel = new Label();

                // 创建垂直布局
                VBox vbox6_1 = new VBox(10); // 设置垂直间距为10
                vbox6_1.getChildren().addAll(button6_1, button6_2, outputLabel);
                vbox6_1.setPadding(new Insets(20)); // 设置内边距

                // 将垂直布局添加到pages[4]的左上角
                StackPane.setAlignment(vbox6_1, Pos.TOP_LEFT);
                pages[5].getChildren().add(vbox6_1);

                // 当按钮被点击时触发事件,进程开始执行
                button6_1.setOnAction(event1 -> startPrinting());
                button6_2.setOnAction(event2 -> stopPrinting());
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}