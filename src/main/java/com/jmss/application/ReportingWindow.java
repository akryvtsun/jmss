package com.jmss.application;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.jmss.domain.Match;
import com.jmss.domain.OverallResult;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class ReportingWindow extends GridPane {
    private static final Logger LOG = Logger.getLogger(ReportingWindow.class.getName());

    private final List<Match> matches;

    private ComboBox<Match> matchComboBox;
    private Label dateLabel;

    public ReportingWindow(List<Match> matches) {
        this.matches = matches;

        prepareComponents();

        setPadding(new Insets(10, 10, 10, 10));
        setHgap(10);
        setVgap(10);

        add(createCoordinatesPane(), 0, 0);
        add(createReportTypePane(), 0, 1);
        add(createMediaPane(), 0, 2);

        add(createButtonsPane(), 0, 3);

        if (!matches.isEmpty()) {
            Match match = matches.get(0);
            matchComboBox.setValue(match);
        }
    }

    private void prepareComponents() {
        matchComboBox = createMatchComboBox(matches);
        dateLabel = new Label("12/34/56");
    }

    private ComboBox<Match> createMatchComboBox(List<Match> matches) {
        ComboBox<Match> comboBox = new ComboBox<>(FXCollections.observableList(matches));
        comboBox.setConverter(new StringConverter<Match>() {
            @Override
            public String toString(Match object) {
                return object.getName();
            }

            @Override
            public Match fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
        comboBox.valueProperty().addListener(e -> {
            LOG.info("Changing match combobox...");

            Match match = comboBox.getValue();

            LocalDate date = match.getDate();
            dateLabel.setText(String.format("%tD", date));
        });
        return comboBox;
    }

    private TitledPane createCoordinatesPane() {

        GridPane pane = new GridPane();
        pane.setHgap(5);

        pane.add(new Label("Match:"), 0, 0);
        pane.add(matchComboBox, 1, 0);
        pane.add(new Label("Date:"), 2, 0);
        pane.add(dateLabel, 3, 0);

        TitledPane p = new TitledPane("Coordinates", pane);
        p.setCollapsible(false);

        return p;
    }

    private TitledPane createReportTypePane() {

        HBox pane = new HBox(5.0);

        ToggleGroup group = new ToggleGroup();

        RadioButton overall = new RadioButton("Overall");
        overall.setToggleGroup(group);
        overall.setSelected(true);

        RadioButton stages = new RadioButton("Stages");
        stages.setToggleGroup(group);

        pane.getChildren().addAll(overall, stages);

        TitledPane p = new TitledPane("Type", pane);
        p.setCollapsible(false);

        return p;
    }

    private TitledPane createMediaPane() {

        HBox pane = new HBox(5.0);

        ToggleGroup group = new ToggleGroup();

        RadioButton preview = new RadioButton("Preview");
        preview.setToggleGroup(group);
        preview.setSelected(true);

        RadioButton file = new RadioButton("File");
        file.setToggleGroup(group);

        pane.getChildren().addAll(preview, file);

        TitledPane p = new TitledPane("Media", pane);
        p.setCollapsible(false);

        return p;
    }

    private Pane createButtonsPane() {

        HBox pane = new HBox(5.0);

        Button okButton = new Button("Ok");
        okButton.setDefaultButton(true);
        okButton.setOnAction(event -> {
            LOG.info("Ok pressed, loading results...");

            WebView browser = new WebView();
            WebEngine webEngine = browser.getEngine();

//            InputStream inputStream = getClass().getResourceAsStream("/overall.html");
//            String result = new BufferedReader(new InputStreamReader(inputStream))
//                    .lines().collect(Collectors.joining("\n"));

            MustacheFactory mf = new DefaultMustacheFactory();
            Mustache mustache = mf.compile("overall.html");
            StringWriter sw = new StringWriter();
            OverallResult overall = new OverallResult();
            try {
                mustache.execute(sw, overall).flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            webEngine.loadContent(sw.toString());

            Stage stage = new Stage();
            stage.setTitle("Results");

            Scene scene = new Scene(browser);
            stage.setScene(scene);
            stage.show();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> {
            LOG.info("Cancel pressed...");
        });

        pane.getChildren().addAll(okButton, cancelButton);

        BorderPane p = new BorderPane();
        p.setRight(pane);

        return p;
    }
}
