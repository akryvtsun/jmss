package com.jmss.application;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.jmss.domain.Match;
import com.jmss.domain.Member;
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

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

// TODO review iText Maven dependencies: may it be smaller?
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

            // get overall results
            Match match = matches.get(0);
            Map<Member, Double> overall = match.overall();

            // create results HTML string
            Map<String, Object> scopes = new HashMap<String, Object>();
            scopes.put("match", match);
            scopes.put("overall", overall.entrySet());

            MustacheFactory mf = new DefaultMustacheFactory();
            Mustache mustache = mf.compile("overall.html");
            StringWriter sw = new StringWriter();

            try {
                mustache.execute(sw, scopes).flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String content = sw.toString();

            // create results PDF file
            try {
                OutputStream file = new FileOutputStream(new File("Test.pdf"));
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, file);
                document.open();
                InputStream is = new ByteArrayInputStream(content.getBytes());
                com.itextpdf.tool.xml.XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
                document.close();
                writer.close();
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // show HTML string
            WebView browser = new WebView();
            WebEngine webEngine = browser.getEngine();
            webEngine.loadContent(content);

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
