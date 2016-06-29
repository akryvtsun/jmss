package com.jmms.application;

import com.jmms.domain.Match;
import javafx.scene.layout.GridPane;

import java.util.List;

public class ReportingWindow extends GridPane {

    private final List<Match> matches;

    public ReportingWindow(List<Match> matches) {
        this.matches = matches;
    }
}
