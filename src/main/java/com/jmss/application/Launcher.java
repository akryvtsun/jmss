package com.jmss.application;

import java.util.ArrayList;
import java.util.List;

import com.jmss.application.windows.MatchesWindow;
import com.jmss.application.windows.MembersWindow;
import com.jmss.application.windows.ReportingWindow;
import com.jmss.application.windows.ScoringWindow;
import com.jmss.domain.DemoDataProvider;
import com.jmss.domain.Match;
import com.jmss.domain.Member;
import com.jmss.infra.Utils;
import javafx.application.Application;
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
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO add icon for Ubuntu: http://ubuntuforums.org/showthread.php?t=1760257
// TODO use javafxpackager.exe for runnable package creation
// TODO order consistent icons???
public final class Launcher extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    private final List<Member> members = new ArrayList();
    private final List<Match> matches = new ArrayList();

    public static void main(String[] args) {
        LOGGER.info("Starting application...");
        launch(args);
        LOGGER.info("Application has stopped!");
    }

    @Override
    public void init() throws Exception {
        LOGGER.debug("Initializing...");

        if (isDemoMode()) {
            members.addAll(DemoDataProvider.createMembers());
            matches.addAll(DemoDataProvider.createMatches(members));
        }
    }

    private boolean isDemoMode() {
        return !getParameters().getUnnamed().isEmpty();
    }

    @Override
    public void start(Stage primaryStage) {
        LOGGER.debug("Creating primary stage...");

        LauncherViewModel model = new LauncherViewModel();

        primaryStage.titleProperty().bind(model.titleProperty());
        Image icon = new Image(Utils.getResource("/icons/icon.png"));
        primaryStage.getIcons().add(icon);

        Scene scene = new Scene(createRootPane(model));
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    private BorderPane createRootPane(LauncherViewModel model) {
        BorderPane root = new BorderPane();

        VBox topContainer = new VBox();
        ToolBar toolBar = createToolBar();
        topContainer.getChildren().add(toolBar);
        root.setTop(topContainer);

        Label label = new Label();
        label.textProperty().bind(model.nameProperty());
        label.setMinHeight(200);
        label.setTextAlignment(TextAlignment.CENTER);
        root.setCenter(label);

        return root;
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
        button.setGraphic(new ImageView("/icons/members.png"));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            Stage stage = new LoggableStage("Membership Administration");
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
        ImageView value = new ImageView("/icons/matches.png");
        value.setFitHeight(50);
        value.setFitWidth(50);
        button.setGraphic(value);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            Stage stage = new LoggableStage("Match Administration");
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
        Button button = new Button("_Scoring");
        ImageView value = new ImageView("/icons/scoring.png");
        value.setFitHeight(50);
        value.setFitWidth(50);
        button.setGraphic(value);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            Stage stage = new LoggableStage("Rapid Scoring");
            Scene scene = new Scene(new ScoringWindow(matches));
            stage.setScene(scene);
            // TODO make centering
            //centerStage(stage, stage.getWidth(), stage.getHeight());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });
        return button;
    }

    private Button createReportingButton() {
        Button button = new Button("_Reporting");
        ImageView value = new ImageView("/icons/reporting.png");
        value.setFitHeight(50);
        value.setFitWidth(50);
        button.setGraphic(value);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            Stage stage = new LoggableStage("Match Reporting");
            Scene scene = new Scene(new ReportingWindow(matches));
            stage.setScene(scene);

            // TODO make centering
            //centerStage(stage, stage.getWidth(), stage.getHeight());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.sizeToScene();

            stage.show();
        });
        return button;
    }

//    private void centerStage(Stage stage, double width, double height) {
//        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX((screenBounds.getWidth() - width) / 2);
//        stage.setY((screenBounds.getHeight() - height) / 2);
//    }
}
