package com.jmss.application.views;

import java.util.List;

import com.jmss.domain.Database;
import com.jmss.domain.Match;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

public class ReportingWindowManualTest {

    public static void main(String[] args) {
        new JFXPanel();

        List<Match> matches = Database.createDemoDatabase().getMatches();
        ReportingWindow window = new ReportingWindow(matches);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setTitle("ReportingWindow");
                Scene scene = new Scene(window);
                stage.setScene(scene);
                // TODO make centering
                //centerStage(stage, stage.getWidth(), stage.getHeight());
                //stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.show();
            }
        });
    }
}
