package com.jmms.application;

import com.jmms.domain.Match;
import com.jmms.domain.Member;
import com.jmms.domain.Stage;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.util.List;

public class ScoringWindow extends GridPane {

    public ScoringWindow(List<Match> matches) {
        setPadding(new Insets(10, 10, 10, 10));
        add(createCoordinatesPane(matches), 0, 0, 2, 1);
        add(createHitsPane(), 0, 1);
        add(createPenaltiesPane(), 0, 2);
        add(new Button("Add"), 1, 3);
    }

    private Pane createCoordinatesPane(List<Match> matches) {
        GridPane pane = new GridPane();

        pane.add(new Label("Match"), 0,0);
        ComboBox<Match> matchComboBox = new ComboBox<>(FXCollections.observableList(matches));
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

        pane.add(new Label("Stage"), 0,1);
        pane.add(new ComboBox<Stage>(), 1,1);

        pane.add(new Label("Competitor"), 0,2);
        pane.add(new ComboBox<Member>(), 1,2);

        return pane;
    }

    private Pane createHitsPane() {
        GridPane pane = new GridPane();

        pane.add(new Label("A Hits:"), 0,0);
        pane.add(new TextField(), 1,0);

        pane.add(new Label("C Hits:"), 0,1);
        pane.add(new TextField(), 1,1);

        pane.add(new Label("D Hits:"), 0,2);
        pane.add(new TextField(), 1,2);

        return pane;
    }

    private Pane createPenaltiesPane() {
        GridPane pane = new GridPane();

        pane.add(new Label("Misses:"), 0,0);
        pane.add(new TextField(), 1,0);

        pane.add(new Label("Penalties:"), 0,1);
        pane.add(new TextField(), 1,1);

        return pane;
    }
}
