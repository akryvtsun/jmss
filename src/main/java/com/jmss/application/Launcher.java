package com.jmss.application;

import com.jmss.application.windows.MatchesWindow;
import com.jmss.application.windows.MembersWindow;
import com.jmss.application.windows.ReportingWindow;
import com.jmss.application.windows.ScoringWindow;
import com.jmss.domain.DemoDataProvider;
import com.jmss.domain.Match;
import com.jmss.domain.Member;
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// TODO pack iText for PDF dependency into result jar file in pom.xml
// TODO disable windows resizing
// TODO add 'Ok' and 'Cancel' buttons to have ability rollback window changes
// TODO allows 'Cancel' button to close windows
// TODO add logging
// TODO add keyboard shortcuts
// TODO add icon for Ubuntu: http://ubuntuforums.org/showthread.php?t=1760257
// TODO use javafxpackager.exe for runnable package creation
// TODO add icon to all windows
// TODO remove unneeded icons
// TODO order consistent icons???
// TODO revise windows layouts and make them appropriate size growing
public class Launcher extends Application {
    private static final Logger LOG = Logger.getLogger(Launcher.class.getName());

    private final List<Member> members = new ArrayList();
    private final List<Match> matches = new ArrayList();

    public static void main(String[] args) {
        LOG.info("Starting application...");

        launch(args);
    }

    @Override
    public void init() throws Exception {
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
        LOG.info("Creating primary stage...");

        // TODO use program version from META-INF/MANIFEST.MF file
        primaryStage.setTitle("JMMS v0.5");
        Image icon = new Image(getClass().getResourceAsStream("/icons/icon.png"));
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

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        primaryStage.show();
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
            LOG.info("Membership Administration opening...");

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
        ImageView value = new ImageView("/icons/matches.png");
        value.setFitHeight(50);
        value.setFitWidth(50);
        button.setGraphic(value);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            LOG.info("Match Administration opening...");

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
        Button button = new Button("_Scoring");
        ImageView value = new ImageView("/icons/scoring.png");
        value.setFitHeight(50);
        value.setFitWidth(50);
        button.setGraphic(value);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            LOG.info("Rapid Scoring opening...");

            Stage stage = new Stage();
            stage.setTitle("Rapid Scoring");
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
            LOG.info("Reporting opening...");

            Stage stage = new Stage();
            stage.setTitle("Match Reporting");
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
