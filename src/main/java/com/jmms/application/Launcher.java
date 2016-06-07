package com.jmms.application;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        Button btn = new Button();
        btn.setText("Shooters");
        btn.setOnAction(e -> System.out.println("Hello World!"));

        Button btn2 = new Button("Match");

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.HORIZONTAL);
        toolBar.getItems().add(btn);
        toolBar.getItems().add(btn2);

        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        Menu menuHelp = new Menu("Help");

        menuBar.getMenus().addAll(menuFile, menuHelp);

        BorderPane root = new BorderPane();
        //root.setTop(toolBar);
        Scene scene = new Scene(/*root*/new VBox(), 300, 250);

        ((VBox)scene.getRoot()).getChildren().addAll(menuBar);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
