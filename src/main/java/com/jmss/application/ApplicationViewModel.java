package com.jmss.application;

import java.io.IOException;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.jmss.application.windows.MatchesWindow;
import com.jmss.application.windows.MembersWindow;
import com.jmss.application.windows.ReportingWindow;
import com.jmss.application.windows.ScoringWindow;
import com.jmss.domain.Database;
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

	private Database database = new Database();

	public ApplicationViewModel() {
		this(loadJarManifest());
	}

	private static Manifest loadJarManifest() {
		try {
			return new JarFile("jmss.jar").getManifest();
		}
		catch (IOException e) {
			return new Manifest();
		}
	}

	ApplicationViewModel(Manifest manifest) {
		Attributes attributes = manifest.getMainAttributes();

		String acronym = Optional.ofNullable(attributes.getValue("Acronym"))
				.orElse("name");
		String version = Optional.ofNullable(attributes.getValue("Version"))
				.orElse("0.0");
		String fullName = Optional.ofNullable(attributes.getValue("Title"))
				.orElse("full_name");

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
		database = Database.createDemoDatabase();
	}

	public ReadOnlyObjectProperty<EventHandler<ActionEvent>> membershipAdminProperty() {
		return membershipAdmin;
	}

	class OpenMembershipAdministration implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Stage stage = new LoggableStage("Membership Administration");
			Scene scene = new Scene(new MembersWindow(database.getMembers()));
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
			Scene scene = new Scene(new MatchesWindow(database));
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
			Scene scene = new Scene(new ScoringWindow(database.getMatches()));
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
			Scene scene = new Scene(new ReportingWindow(database.getMatches()));
			stage.setScene(scene);

			// TODO make centering
			//centerStage(stage, stage.getWidth(), stage.getHeight());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.sizeToScene();

			stage.show();
		}
	}
}
