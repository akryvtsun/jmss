package com.jmms.application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
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
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        Button btn2 = new Button("Match");

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.HORIZONTAL);
        toolBar.getItems().add(btn);
        toolBar.getItems().add(btn2);

        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
