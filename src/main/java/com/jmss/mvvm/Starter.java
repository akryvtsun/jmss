package com.jmss.mvvm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// TODO remove this example folder and LoginView.fxml and LoginViewModelTest.java
public class Starter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent login = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(login);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
