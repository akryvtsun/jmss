package com.jmms.application;

import com.jmms.domain.Member;
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

public class StagesWindow extends BorderPane {

    static ObservableList<Member> Data = FXCollections.observableArrayList();

    private final TabPane tabPane = new TabPane();

    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();

    private final TableView<Member> table = new TableView<>();

    public StagesWindow() {
        setCenter(createTabPane());
        setRight(createButtonPane());
    }

    private TabPane createTabPane() {
        Tab memberTab = new Tab("Stage");
        memberTab.setContent(createStageTab());

        Tab memberListTab = new Tab("Stages List");
        memberListTab.setContent(createStagesListTab());

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                        TableView.TableViewSelectionModel<Member> tableSelectionModel = table.getSelectionModel();
                        int index = tableSelectionModel.getSelectedIndex();
                        if (index >= 0) {
                            if (memberListTab.equals(newValue)) {
                                Member person = new Member(firstNameField.getText(), lastNameField.getText());

                                Data.set(index, person);
                            } else if (memberTab.equals(newValue)) {
                                Member person = Data.get(index);

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

    private Pane createStageTab() {
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

    private Pane createStagesListTab() {
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("lastName"));

        table.setEditable(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    TableView.TableViewSelectionModel<Member> tableSelectionModel = table.getSelectionModel();
                    int index = tableSelectionModel.getSelectedIndex();
                    if (index >= 0) {
                        Member person = Data.get(index);

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
            Member person = new Member(firstNameField.getText(), lastNameField.getText());

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
}
