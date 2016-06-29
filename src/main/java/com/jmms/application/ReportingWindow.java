package com.jmms.application;

import com.jmms.domain.Match;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

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
//        //pane.setStyle("-fx-background-color: #cccccc; -fx-border-color: #464646; ");
//        //pane.setPrefWidth(Double.MAX_VALUE);
//        pane.setHgap(5);
//        pane.setVgap(5);

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
//        //pane.setStyle("-fx-background-color: #cccccc; -fx-border-color: #464646; ");
//        //pane.setPrefWidth(Double.MAX_VALUE);
//        pane.setHgap(5);
//        pane.setVgap(5);

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
        okButton.setOnAction(event -> {
            LOG.info("Ok pressed...");
        });

        Button cancelButton = new Button("Cancel");
        okButton.setOnAction(event -> {
            LOG.info("Cancel pressed...");
        });

        pane.getChildren().addAll(okButton, cancelButton);

        BorderPane p = new BorderPane();
        p.setRight(pane);

        return p;
    }
}
