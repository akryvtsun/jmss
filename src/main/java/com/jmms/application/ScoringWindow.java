package com.jmms.application;

import com.jmms.domain.Match;
import com.jmms.domain.Member;
import com.jmms.domain.Stage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.util.StringConverter;

import java.util.Collections;
import java.util.List;

public class ScoringWindow extends GridPane {

    private Spinner<Integer> aHits;
    private Spinner<Integer> cHits;
    private Spinner<Integer> dHits;
    private Spinner<Integer> misses;
    private Spinner<Integer> penalties;

    public ScoringWindow(List<Match> matches) {
        setPadding(new Insets(10, 10, 10, 10));
        setHgap(5);
        setVgap(5);

        aHits = createSpinner();
        cHits = createSpinner();
        dHits = createSpinner();
        misses = createSpinner();
        penalties = createSpinner();

        add(createCoordinatesPane(matches), 0, 0);
        Separator child = new Separator();
        add(child, 0, 1);
        add(createHitsPane(), 0, 2);
        add(new Separator(), 0, 3);
//        add(createPenaltiesPane(), 0, 4);

        add(createButtonsPane(), 0, 4);
    }

    public static void main(String[] args) {
        new JFXPanel();
        ScoringWindow window = new ScoringWindow(Collections.EMPTY_LIST);

        Platform.runLater(new Runnable() {
            @Override public void run() {
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setTitle("Rapid Scoring");
                Scene scene = new Scene(window);
                stage.setScene(scene);
                // TODO make centering
                //centerStage(stage, stage.getWidth(), stage.getHeight());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
        });
    }

    private Pane createCoordinatesPane(List<Match> matches) {
        GridPane pane = new GridPane();
        //pane.setStyle("-fx-background-color: #cccccc; -fx-border-color: #464646; ");
        //pane.setPrefWidth(Double.MAX_VALUE);
        pane.setHgap(5);
        pane.setVgap(5);

        pane.add(new Label("Match:"), 0,0);
        ComboBox<Match> matchComboBox = new ComboBox<>(FXCollections.observableList(matches));
        //matchComboBox.setPrefWidth(Double.MAX_VALUE);
        matchComboBox.setConverter(new StringConverter<Match>() {
            @Override
            public String toString(Match object) {
                return object.getName();
            }

            @Override
            public Match fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
        pane.add(matchComboBox, 1,0);

        pane.add(new Label("Stage:"), 0,1);
        pane.add(new ComboBox<Stage>(), 1,1);

        pane.add(new Label("Competitor:"), 0,2);
        pane.add(new ComboBox<Member>(), 1,2);

        return pane;
    }

    private Pane createHitsPane() {
        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);
        //pane.setStyle("-fx-background-color: orange; -fx-border-color: #464646; ");

        pane.add(new Label("A Hits:"), 0,0);
        pane.add(aHits, 1,0);

        pane.add(new Label("C Hits:"), 0,1);
        pane.add(cHits, 1,1);

        pane.add(new Label("D Hits:"), 0,2);
        pane.add(dHits, 1,2);

        pane.add(new Label("Misses:"), 2,0);
        pane.add(misses, 3,0);

        pane.add(new Label("Penalties:"), 2,1);
        pane.add(penalties, 3,1);

        return pane;
    }

    private Spinner createSpinner() {
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setEditable(true);
        SpinnerValueFactory svf =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99);
        spinner.setValueFactory(svf);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        return spinner;
    }

    private Pane createButtonsPane() {
        BorderPane pane = new BorderPane();
        pane.setRight(new Button("Add"));
        return pane;
    }
}
