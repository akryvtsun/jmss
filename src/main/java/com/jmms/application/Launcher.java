package com.jmms.application;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Optional;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JMMS v0.1");
        primaryStage.setResizable(false);

        BorderPane root = new BorderPane();

        VBox topContainer = new VBox();
        ToolBar toolBar = createToolBar();
        topContainer.getChildren().add(toolBar);

        root.setTop(topContainer);

        double width = 320;
        double height = 240;

        centerStage(primaryStage, width, height);

        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();

        Button membersBtn = createMembersButton();

        Button matchesBtn = new Button("Matches");
        matchesBtn.setGraphic(new ImageView("/matches.png"));
        matchesBtn.setContentDisplay(ContentDisplay.TOP);
        matchesBtn.setOnAction(e -> System.out.println("Hello World!"));

        Button scoringBtn = new Button("Scoring");
        scoringBtn.setGraphic(new ImageView("/scoring.png"));
        scoringBtn.setContentDisplay(ContentDisplay.TOP);

        Button reportingBtn = new Button("Reporting");
        reportingBtn.setGraphic(new ImageView("/reporting.png"));
        reportingBtn.setContentDisplay(ContentDisplay.TOP);

        toolBar.getItems().addAll(membersBtn, matchesBtn, scoringBtn, reportingBtn);
        return toolBar;
    }

    private Button createMembersButton() {
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
        return membersBtn;
    }

    private void centerStage(Stage primaryStage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);
    }
}
