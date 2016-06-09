package com.jmms.application;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
        Button matchesBtn = createMatchesButton();

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
        Button button = new Button("Members");
        button.setGraphic(new ImageView("/members.png"));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Membership Administration");
            Scene scene = new Scene(new MembersWindow());
            stage.setScene(scene);
            stage.show();
        });
        return button;
    }

    private Button createMatchesButton() {
        Button button = new Button("Matches");
        button.setGraphic(new ImageView("/matches.png"));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Match Administration");
            Scene scene = new Scene(new MatchesWindow());
            stage.setScene(scene);
            stage.show();
        });
        return button;
    }

    private void centerStage(Stage primaryStage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);
    }
}
