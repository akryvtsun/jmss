package com.jmss.application;

import com.jmss.domain.Match;
import com.jmss.infra.results.OverallHtmlResult;
import com.jmss.infra.PdfReport;
import com.jmss.infra.results.HtmlResult;
import com.jmss.infra.results.StagesHtmlResult;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

// TODO review iText Maven dependencies: may it be smaller?
public class ReportingWindow extends GridPane {
    private static final Logger LOG = Logger.getLogger(ReportingWindow.class.getName());

    private final List<Match> matches;

    private ComboBox<Match> matchComboBox;
    private Label dateLabel;

    private RadioButton overallButton;
    private RadioButton previewButton;

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

        overallButton = new RadioButton("Overall");
        overallButton.setToggleGroup(group);
        overallButton.setSelected(true);

        RadioButton stages = new RadioButton("Stages");
        stages.setToggleGroup(group);

        pane.getChildren().addAll(overallButton, stages);

        TitledPane p = new TitledPane("Report Type", pane);
        p.setCollapsible(false);

        return p;
    }

    private TitledPane createMediaPane() {

        HBox pane = new HBox(5.0);

        ToggleGroup group = new ToggleGroup();

        previewButton = new RadioButton("Preview");
        previewButton.setToggleGroup(group);
        previewButton.setSelected(true);

        RadioButton file = new RadioButton("File");
        file.setToggleGroup(group);

        pane.getChildren().addAll(previewButton, file);

        TitledPane p = new TitledPane("Output Media", pane);
        p.setCollapsible(false);

        return p;
    }

    private Pane createButtonsPane() {

        HBox pane = new HBox(5.0);

        Button okButton = new Button("Ok");
        okButton.setDefaultButton(true);
        okButton.setOnAction(event -> {
            LOG.info("Ok pressed, loading results...");

            Match match = matchComboBox.getValue();

            // get results
            HtmlResult results = overallButton.isSelected()
                    ? new OverallHtmlResult(match)
                    : new StagesHtmlResult(match);

            if (previewButton.isSelected()) {
                // show HTML string
                WebView browser = new WebView();
                WebEngine webEngine = browser.getEngine();
                webEngine.loadContent(results.toHtml(false));
                webEngine.setUserStyleSheetLocation(getClass().getResource("/reports/styles.css").toString());

                Stage stage = new Stage();
                stage.setTitle("Results");

                Scene scene = new Scene(browser);
                stage.setScene(scene);
                stage.show();
            } else {
                // create results PDF file
                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("Save Report File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));

                fileChooser.setInitialDirectory(new File("."));
                fileChooser.setInitialFileName(results.getInitialFileName());

                Window ownerWindow = getScene().getWindow();
                File file = fileChooser.showSaveDialog(ownerWindow);

                if (file != null) {
                    PdfReport report = new PdfReport(file);
                    report.save(results.toHtml(true));
                }
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(event -> {
            LOG.info("Cancel pressed...");
            getScene().getWindow().hide();
        });

        pane.getChildren().addAll(okButton, cancelButton);

        BorderPane p = new BorderPane();
        p.setRight(pane);

        return p;
    }
}
