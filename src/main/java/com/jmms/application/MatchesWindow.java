package com.jmms.application;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MatchesWindow extends BorderPane {

    public MatchesWindow() {
        ToolBar toolBar = new ToolBar();

        Button stages = new Button("Stages");
        stages.setGraphic(new ImageView("/stages.png"));
        stages.setContentDisplay(ContentDisplay.TOP);

        Button competitors = new Button("Competitors");
        competitors.setGraphic(new ImageView("/competitors.png"));
        competitors.setContentDisplay(ContentDisplay.TOP);

        toolBar.getItems().addAll(stages, competitors);

        setTop(toolBar);
    }
}
