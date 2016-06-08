package com.jmms.application;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Launcher extends Application {

    final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith"),
            new Person("Isabella", "Johnson"),
            new Person("Ethan", "Williams"),
            new Person("Emma", "Jones"),
            new Person("Michael", "Brown")
    );

    TableView<Person> table = new TableView<Person>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JMMS v0.1");
        primaryStage.setResizable(false);

        BorderPane root = new BorderPane();

        VBox topContainer = new VBox();
        ToolBar toolBar = createToolBar();
        topContainer.getChildren().add(toolBar);

        root.setTop(topContainer);

        double width = 320;
        double height = 240;

        centerStage(primaryStage, width, height);

        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();

        Button membersBtn = createMembersButton();

        Button matchesBtn = new Button("Matches");
        matchesBtn.setGraphic(new ImageView("/matches.png"));
        matchesBtn.setContentDisplay(ContentDisplay.TOP);
        matchesBtn.setOnAction(e -> System.out.println("Hello World!"));

        Button scoringBtn = new Button("Scoring");
        scoringBtn.setGraphic(new ImageView("/scoring.png"));
        scoringBtn.setContentDisplay(ContentDisplay.TOP);

        Button reportingBtn = new Button("Reporting");
        reportingBtn.setGraphic(new ImageView("/reporting.png"));
        reportingBtn.setContentDisplay(ContentDisplay.TOP);

        toolBar.getItems().addAll(membersBtn, matchesBtn, scoringBtn, reportingBtn);
        return toolBar;
    }

    private Button createMembersButton() {
        Button membersBtn = new Button("Members");
        membersBtn.setGraphic(new ImageView("/members.png"));
        membersBtn.setContentDisplay(ContentDisplay.TOP);
        membersBtn.setOnAction(e -> {
            Stage dialog = new Stage();

            BorderPane bPane = new BorderPane();
            //bPane.setPadding(new Insets(10, 10, 10, 10));
            bPane.setCenter(createTabPane());
            bPane.setRight(createButtonPanel());

            Scene scene = new Scene(bPane);
            dialog.setScene(scene);
            dialog.show();

//            ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
//            Dialog<String> dialog = new Dialog<>();
//            dialog.getDialogPane().getButtonTypes().add(loginButtonType);
//            boolean disabled = false; // computed based on content of text fields, for example
//            dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
//            Optional<String> optional = dialog.showAndWait();
        });
        return membersBtn;
    }

    private Node createButtonPanel() {
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        Button aNew = new Button("New");
        aNew.setOnAction(e -> {
            data.add(new Person(
                    "Name 1",
                    "Surname 1"
            ));
        });
        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            int focusedIndex = table.getSelectionModel().getFocusedIndex();
            data.remove(focusedIndex);
        });
        vbox.getChildren().addAll(aNew, delete);
        return vbox;
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab memberTab = new Tab("Member");
        HBox hbox = new HBox();
        hbox.getChildren().add(new Label("Tab 1"));
        hbox.setAlignment(Pos.CENTER);
        memberTab.setContent(hbox);

        /////

        Tab membersTab = new Tab("Member List");

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));

        table.setEditable(true);

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(table);
        membersTab.setContent(vbox);

        tabPane.getTabs().add(memberTab);
        tabPane.getTabs().add(membersTab);
        return tabPane;
    }

    private void centerStage(Stage primaryStage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);
    }

    public static class Person {
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;

        private Person(String fName, String lName) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }
    }
}
