package com.jmms.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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

        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();

        //// create menu

        MenuBar mainMenu = new MenuBar();

        Menu file = new Menu("File");
        MenuItem exitApp = new MenuItem("Exit");
        file.getItems().addAll(exitApp);

        Menu help = new Menu("Help");
        MenuItem aboutDlg = new MenuItem("About");
        help.getItems().addAll(aboutDlg);

        mainMenu.getMenus().addAll(file, help);

        //// create toolbar

        ToolBar toolBar = new ToolBar();

        Button membersBtn = new Button("Members");
        membersBtn.setGraphic(new ImageView("/members.png"));

        membersBtn.setOnAction(e -> System.out.println("Hello World!"));
        Button matchesBtn = new Button("Matches");

        toolBar.getItems().addAll(membersBtn, matchesBtn);

        ////

        topContainer.getChildren().add(mainMenu);
        topContainer.getChildren().add(toolBar);

        root.setTop(topContainer);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
