package com.jmss.application;

import com.jmss.application.models.ApplicationViewModel;
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
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO add icon for Ubuntu: http://ubuntuforums.org/showthread.php?t=1760257
// TODO order consistent icons???
public class JmssApplication extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(JmssApplication.class);

	private final ApplicationViewModel model;

	public JmssApplication() {
		 model = new ApplicationViewModel();
	}

	@Override
	public void init() throws Exception {
		LOGGER.debug("Initializing...");

		if (isDemoMode()) {
			model.loadDemoData();
		}
	}

	private boolean isDemoMode() {
		return !getParameters().getUnnamed().isEmpty();
	}

	@Override
	public void start(Stage primaryStage) {
		LOGGER.debug("Creating primary stage...");

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

	private BorderPane createRootPane(ApplicationViewModel model) {
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
		button.onActionProperty().bind(model.membershipAdminProperty());
		return button;
	}

	private Button createMatchesButton() {
		Button button = new Button("M_atches");
		ImageView value = new ImageView("/icons/matches.png");
		value.setFitHeight(50);
		value.setFitWidth(50);
		button.setGraphic(value);
		button.setContentDisplay(ContentDisplay.TOP);
		button.onActionProperty().bind(model.matchAdminProperty());
		return button;
	}

	private Button createScoringButton() {
		Button button = new Button("_Scoring");
		ImageView value = new ImageView("/icons/scoring.png");
		value.setFitHeight(50);
		value.setFitWidth(50);
		button.setGraphic(value);
		button.setContentDisplay(ContentDisplay.TOP);
		button.onActionProperty().bind(model.rapidScoringProperty());
		return button;
	}

	private Button createReportingButton() {
		Button button = new Button("_Reporting");
		ImageView value = new ImageView("/icons/reporting.png");
		value.setFitHeight(50);
		value.setFitWidth(50);
		button.setGraphic(value);
		button.setContentDisplay(ContentDisplay.TOP);
		button.onActionProperty().bind(model.matchReportingProperty());
		return button;
	}
}
