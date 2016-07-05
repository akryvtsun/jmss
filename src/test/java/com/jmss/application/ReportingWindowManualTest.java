package com.jmss.application;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Modality;

/**
 * Created by ax01220 on 6/29/2016.
 */
public class ReportingWindowManualTest {

    public static void main(String[] args) {
        new JFXPanel();

        ReportingWindow window = new ReportingWindow(DataHelper.createMatches());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setTitle("ReportingWindow");
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
