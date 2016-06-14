package com.jmms.application;

import com.jmms.domain.Match;
import com.jmms.domain.Member;
import com.jmms.domain.Stage;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;

public class ScoringWindow extends GridPane {

    public ScoringWindow(List<Match> matches) {
        setPadding(new Insets(10, 10, 10, 10));

        add(new Label("Match"), 0,0);
        add(new ComboBox<Match>(FXCollections.observableList(matches)), 1,0);

        add(new Label("Stage"), 0,1);
        add(new ComboBox<Stage>(), 1,1);

        add(new Label("Competitor"), 0,2);
        add(new ComboBox<Member>(), 1,2);
    }
}
