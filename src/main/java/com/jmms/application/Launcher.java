package com.jmms.application;

import com.jmms.domain.Match;
import com.jmms.domain.Member;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

// TODO disable windows resizing
// TODO add 'Ok' and 'Cancel' buttons to have ability rollback window changes
// TODO allows 'Cancel' button to close windows
// TODO add logging
// TODO add keyboard shortcuts
public class Launcher extends Application {

    private List<Member> members = new ArrayList();
    private List<Match> matches = new ArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JMMS v0.1");
        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);

        BorderPane root = new BorderPane();

        VBox topContainer = new VBox();
        ToolBar toolBar = createToolBar();
        topContainer.getChildren().add(toolBar);
        root.setTop(topContainer);

        Label label = new Label("jMatch Scoring System\n(c) 2016");
        label.setMinHeight(200);
        label.setTextAlignment(TextAlignment.CENTER);
        root.setCenter(label);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        Platform.runLater(() -> {
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        });
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();

        Button membersBtn = createMembersButton();
        Button matchesBtn = createMatchesButton();
        Button scoringBtn = createScoringButton();
        Button reportingBtn = createReportingButton();

        toolBar.getItems().addAll(membersBtn, matchesBtn, scoringBtn, reportingBtn);
        return toolBar;
    }

    private Button createMembersButton() {
        Button button = new Button("_Members");
        button.setGraphic(new ImageView("/members.png"));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Membership Administration");
            Scene scene = new Scene(new MembersWindow(members));
            stage.setScene(scene);
            // TODO make centering
            //centerStage(stage, stage.getWidth(), stage.getHeight());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });
        return button;
    }

    private Button createMatchesButton() {
        Button button = new Button("M_atches");
        ImageView value = new ImageView("/matches.png");
        value.setFitHeight(50);
        value.setFitWidth(50);
        button.setGraphic(value);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Match Administration");
            Scene scene = new Scene(new MatchesWindow(members, matches));
            stage.setScene(scene);
            // TODO make centering
            //centerStage(stage, stage.getWidth(), stage.getHeight());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });
        return button;
    }

    private Button createScoringButton() {
        Button scoringBtn = new Button("_Scoring");
        ImageView value = new ImageView("/scoring.png");
        value.setFitHeight(50);
        value.setFitWidth(50);
        scoringBtn.setGraphic(value);
        scoringBtn.setContentDisplay(ContentDisplay.TOP);
        scoringBtn.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Rapid Scoring");
            Scene scene = new Scene(new ScoringWindow(matches));
            stage.setScene(scene);
            // TODO make centering
            //centerStage(stage, stage.getWidth(), stage.getHeight());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });
        return scoringBtn;
    }

    private Button createReportingButton() {
        Button reportingBtn = new Button("_Reporting");
        ImageView value1 = new ImageView("/reporting.png");
        value1.setFitHeight(50);
        value1.setFitWidth(50);
        reportingBtn.setGraphic(value1);
        reportingBtn.setContentDisplay(ContentDisplay.TOP);
        reportingBtn.setDisable(true);
        return reportingBtn;
    }

    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }
}
