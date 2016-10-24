package com.jmss.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.jmss.application.windows.MatchesWindow;
import com.jmss.application.windows.MembersWindow;
import com.jmss.application.windows.ReportingWindow;
import com.jmss.application.windows.ScoringWindow;
import com.jmss.domain.DemoDataProvider;
import com.jmss.domain.Match;
import com.jmss.domain.Member;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ApplicationViewModel {

	private String acronym = "name";
	private String version = "0.0";
	private String fullName = "full_name";

	private StringProperty title = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();

	private ReadOnlyObjectWrapper<EventHandler<ActionEvent>> membershipAdmin =
			new ReadOnlyObjectWrapper(new OpenMembershipAdministration());

	private ReadOnlyObjectWrapper<EventHandler<ActionEvent>> matchAdmin =
			new ReadOnlyObjectWrapper(new OpenMatchAdministration());

	private ReadOnlyObjectWrapper<EventHandler<ActionEvent>> rapidScoring =
			new ReadOnlyObjectWrapper(new OpenRapidScoring());

	private ReadOnlyObjectWrapper<EventHandler<ActionEvent>> matchReporting =
			new ReadOnlyObjectWrapper(new OpenMatchReporting());

	private final List<Member> members = new ArrayList();
	private final List<Match> matches = new ArrayList();

	public ApplicationViewModel() {
		try {
			JarFile jar = new JarFile("jmss.jar");
			Manifest manifest = jar.getManifest();
			Attributes attributes = manifest.getMainAttributes();
			acronym = attributes.getValue("Acronym");
			version = attributes.getValue("Version");
			fullName = attributes.getValue("Title");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		title.setValue(String.format("%s v%s", acronym, version));
		name.setValue(String.format("%s\n(c) 2016", fullName));
	}

	public StringProperty titleProperty() {
		return title;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void loadDemoData() {
		members.addAll(DemoDataProvider.createMembers());
		matches.addAll(DemoDataProvider.createMatches(members));
	}

	public ReadOnlyObjectProperty<EventHandler<ActionEvent>> membershipAdminProperty() {
		return membershipAdmin;
	}

	class OpenMembershipAdministration implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Stage stage = new LoggableStage("Membership Administration");
			Scene scene = new Scene(new MembersWindow(members));
			stage.setScene(scene);
			// TODO make centering
			//centerStage(stage, stage.getWidth(), stage.getHeight());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		}
	}

	public ReadOnlyObjectProperty<EventHandler<ActionEvent>> matchAdminProperty() {
		return matchAdmin;
	}

	class OpenMatchAdministration implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Stage stage = new LoggableStage("Match Administration");
			Scene scene = new Scene(new MatchesWindow(members, matches));
			stage.setScene(scene);
			// TODO make centering
			//centerStage(stage, stage.getWidth(), stage.getHeight());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		}
	}

	public ReadOnlyObjectProperty<EventHandler<ActionEvent>> rapidScoringProperty() {
		return rapidScoring;
	}

	class OpenRapidScoring implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Stage stage = new LoggableStage("Rapid Scoring");
			Scene scene = new Scene(new ScoringWindow(matches));
			stage.setScene(scene);
			// TODO make centering
			//centerStage(stage, stage.getWidth(), stage.getHeight());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		}
	}

	public ReadOnlyObjectProperty<EventHandler<ActionEvent>> matchReportingProperty() {
		return matchReporting;
	}

	class OpenMatchReporting implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Stage stage = new LoggableStage("Match Reporting");
			Scene scene = new Scene(new ReportingWindow(matches));
			stage.setScene(scene);

			// TODO make centering
			//centerStage(stage, stage.getWidth(), stage.getHeight());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.sizeToScene();

			stage.show();
		}
	}

//    private void centerStage(Stage stage, double width, double height) {
//        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX((screenBounds.getWidth() - width) / 2);
//        stage.setY((screenBounds.getHeight() - height) / 2);
//    }
}
