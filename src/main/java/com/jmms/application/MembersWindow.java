package com.jmms.application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

// TODO disable Delete button if table is empty
// TODO disable make whole window smaller (or even remove resize ability at all)
public class MembersWindow extends BorderPane {

    static ObservableList<Person> Data = FXCollections.observableArrayList();

    private final TabPane tabPane = new TabPane();

    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();

    private final TableView<Person> table = new TableView<>();

    public MembersWindow() {
        setCenter(createTabPane());
        setRight(createButtonPane());
    }

    private TabPane createTabPane() {
        Tab memberTab = new Tab("Member");
        memberTab.setContent(createMemberTab());

        Tab memberListTab = new Tab("Member List");
        memberListTab.setContent(createMemberListTab());

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                        TableView.TableViewSelectionModel<Person> tableSelectionModel = table.getSelectionModel();
                        int index = tableSelectionModel.getSelectedIndex();
                        if (index >= 0) {
                            if (memberListTab.equals(newValue)) {
                                Person person = new Person(firstNameField.getText(), lastNameField.getText());

                                Data.set(index, person);
                            } else if (memberTab.equals(newValue)) {
                                Person person = Data.get(index);

                                firstNameField.setText(person.getFirstName());
                                lastNameField.setText(person.getLastName());
                            }
                        }
                    }
                }
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().add(memberTab);
        tabPane.getTabs().add(memberListTab);

        return tabPane;
    }

    private Pane createMemberTab() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        Label label1 = new Label("First Name:");
        GridPane.setConstraints(label1, 0, 0);
        pane.getChildren().add(label1);

        GridPane.setConstraints(firstNameField, 1, 0);
        pane.getChildren().add(firstNameField);

        Label label2 = new Label("Last Name:");
        GridPane.setConstraints(label2, 0, 1);
        pane.getChildren().add(label2);

        GridPane.setConstraints(lastNameField, 1, 1);
        pane.getChildren().add(lastNameField);

        return pane;
    }

    private Pane createMemberListTab() {
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(Control.USE_PREF_SIZE);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(Control.USE_PREF_SIZE);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        table.setEditable(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    TableView.TableViewSelectionModel<Person> tableSelectionModel = table.getSelectionModel();
                    int index = tableSelectionModel.getSelectedIndex();
                    if (index >= 0) {
                        Person person = Data.get(index);

                        firstNameField.setText(person.getFirstName());
                        lastNameField.setText(person.getLastName());

                        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                        selectionModel.select(0);
                    }
                }
            }
        });

        table.setItems(Data);
        table.getColumns().

        addAll(firstNameCol, lastNameCol);

        GridPane pane = new GridPane();

        pane.setPadding(new Insets(10, 0, 10, 10));
        GridPane.setHgrow(table, Priority.ALWAYS);
        GridPane.setVgrow(table, Priority.ALWAYS);
        pane.getChildren().addAll(table);

        return pane;
    }

    private Node createButtonPane() {
        final VBox pane = new VBox();
        pane.setSpacing(5);
        pane.setPadding(new Insets(40, 10, 10, 10));

        Button aNew = new Button("New");
        aNew.setMaxWidth(Double.MAX_VALUE);
        aNew.setOnAction(e -> {
            Person person = new Person(firstNameField.getText(), lastNameField.getText());

            Data.add(person);

            firstNameField.clear();
            lastNameField.clear();
        });

        Button delete = new Button("Delete");
        delete.setMaxWidth(Double.MAX_VALUE);
        delete.setOnAction(e -> {
            int focusedIndex = table.getSelectionModel().getFocusedIndex();
            if (focusedIndex >= 0)
                Data.remove(focusedIndex);
        });

        pane.getChildren().addAll(aNew, delete);
        return pane;
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
