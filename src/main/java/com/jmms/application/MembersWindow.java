package com.jmms.application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MembersWindow extends BorderPane {

    final static ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith"),
            new Person("Isabella", "Johnson"),
            new Person("Ethan", "Williams"),
            new Person("Emma", "Jones"),
            new Person("Michael", "Brown")
    );

    final TextField addFirstName = new TextField();
    final TextField addLastName = new TextField();

    TableView<Person> table = new TableView<Person>();

    public MembersWindow() {
        //bPane.setPadding(new Insets(10, 10, 10, 10));
        setCenter(createTabPane());
        setRight(createButtonPanel());
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab memberTab = new Tab("Member");
        VBox hbox = new VBox();

        addFirstName.setPromptText("First Name");
        //addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        //addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");

        hbox.getChildren().add(addFirstName);
        hbox.getChildren().add(addLastName);

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
        vbox.setPadding(new Insets(10, 0, 10, 10));
        vbox.getChildren().addAll(table);
        membersTab.setContent(vbox);

        tabPane.getTabs().add(memberTab);
        tabPane.getTabs().add(membersTab);
        return tabPane;
    }

    private Node createButtonPanel() {
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(30, 10, 10, 10));

        Button aNew = new Button("New");
        aNew.setMaxWidth(Double.MAX_VALUE);
        aNew.setOnAction(e -> {
            data.add(new Person(addFirstName.getText(), addLastName.getText()));
            addFirstName.clear();
            addLastName.clear();
        });

        Button delete = new Button("Delete");
        delete.setMaxWidth(Double.MAX_VALUE);
        delete.setOnAction(e -> {
            int focusedIndex = table.getSelectionModel().getFocusedIndex();
            data.remove(focusedIndex);
        });

        vbox.getChildren().addAll(aNew, delete);
        return vbox;
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
