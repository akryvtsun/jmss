package com.jmss.application.windows;

import com.jmss.application.windows.ScoringWindow;
import com.jmss.domain.DemoDataProvider;
import com.jmss.domain.Match;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Modality;

import java.util.List;

public class ScoringWindowManualTest {

    public static void main(String[] args) {
        new JFXPanel();

        List<Match> matches = DemoDataProvider.createMatches(DemoDataProvider.createMembers());
        ScoringWindow window = new ScoringWindow(matches);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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
}