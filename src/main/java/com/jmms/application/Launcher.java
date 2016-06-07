package com.jmms.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        //primaryStage.initStyle(StageStyle.UTILITY);

        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();

        //// create menu

        MenuBar mainMenu = new MenuBar();

        Menu file = new Menu("File");
        MenuItem exitApp = new MenuItem("Exit");
        exitApp.setOnAction(e -> System.exit(0));
        file.getItems().addAll(exitApp);

        Menu help = new Menu("Help");
        MenuItem aboutDlg = new MenuItem("About");
        help.getItems().addAll(aboutDlg);

        mainMenu.getMenus().addAll(file, help);

        //// create toolbar

        ToolBar toolBar = new ToolBar();

        Button membersBtn = new Button("Members");
        membersBtn.setGraphic(new ImageView("/members.png"));
        membersBtn.setContentDisplay(ContentDisplay.TOP);
        membersBtn.setOnAction(e -> {
//            Stage dialog = new Stage();
//            dialog.initStyle(StageStyle.UTILITY);
//            Scene scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
//            dialog.setScene(scene);
//            dialog.show();

            ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
            Dialog<String> dialog = new Dialog<>();
            dialog.getDialogPane().getButtonTypes().add(loginButtonType);
            boolean disabled = false; // computed based on content of text fields, for example
            dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
            Optional<String> optional = dialog.showAndWait();
        });

        Button matchesBtn = new Button("Matches");
        matchesBtn.setGraphic(new ImageView("/matches.png"));
        matchesBtn.setContentDisplay(ContentDisplay.TOP);
        matchesBtn.setOnAction(e -> System.out.println("Hello World!"));

        Button scoringBtn = new Button("Scoring");
        scoringBtn.setGraphic(new ImageView("/scoring.png"));
        scoringBtn.setContentDisplay(ContentDisplay.TOP);

        toolBar.getItems().addAll(membersBtn, matchesBtn, scoringBtn);

        ////

        topContainer.getChildren().add(mainMenu);
        topContainer.getChildren().add(toolBar);

        root.setTop(topContainer);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
