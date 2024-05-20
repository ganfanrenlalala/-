package org.example;


import javafx.application.Application;
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
import java.util.Arrays;




public class DrawerExample extends Application {

    BorderPane root;
    StackPane[] pages;

    MyString myString;

    Graph myGraph;

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

    private void setPages(int i){
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
                button1.setOnAction(event -> {
                    // 获取输入框中的文本
                    String text = textField1.getText();
                    myString=new MyString(text);
                    //****调用程序*****//
                    // 创建一个新的标签，显示按钮点击后的文本
                    Label label = new Label(Arrays.toString(myString.word_array));
                    // 将标签添加到根节点中
                    pages[0].getChildren().add(label);
                    myGraph=new Graph(myString.word_array);
                });
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



        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

