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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class CompetitorsWindow extends BorderPane {

    private final ObservableList<Member> membersData;
    private final ObservableList<Member> data;

    private final TabPane tabPane = new TabPane();

    private final TableView<Member> membersTable = new TableView<>();
    private final TableView<Member> table = new TableView<>();

    public CompetitorsWindow(List<Member> members, List<Member> competitors) {
        membersData = FXCollections.observableList(members);
        data = FXCollections.observableList(competitors);

        setCenter(createTabPane());
        setRight(createButtonPane());
    }

    private TabPane createTabPane() {
        Tab competitorTab = new Tab("Competitor");
        competitorTab.setContent(createCompetitorTab());

        Tab competitorListTab = new Tab("Competitor List");
        competitorListTab.setContent(createCompetitorListTab());

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                        TableView.TableViewSelectionModel<Member> tableSelectionModel = table.getSelectionModel();
                        int index = tableSelectionModel.getSelectedIndex();
//                        if (index >= 0) {
//                            if (competitorListTab.equals(newValue)) {
//                                Member member = new Member(firstNameField.getText(), lastNameField.getText());
//
//                                Data.set(index, member);
//                            } else if (competitorTab.equals(newValue)) {
//                                Member member = Data.get(index);
//
//                                firstNameField.setText(member.getFirstName());
//                                lastNameField.setText(member.getLastName());
//                            }
//                        }
                    }
                }
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().add(competitorTab);
        tabPane.getTabs().add(competitorListTab);

        return tabPane;
    }

    private Pane createCompetitorTab() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 10, 10, 10));

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("lastName"));

        membersTable.setEditable(true);
        membersTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                if (event.getClickCount() > 1) {
//                    TableView.TableViewSelectionModel<Member> tableSelectionModel = table.getSelectionModel();
//                    int index = tableSelectionModel.getSelectedIndex();
//                    if (index >= 0) {
//                        Member member = data.get(index);
//
//                        firstNameField.setText(member.getFirstName());
//                        lastNameField.setText(member.getLastName());
//
//                        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
//                        selectionModel.select(0);
//                    }
//                }
            }
        });

        membersTable.getColumns().addAll(firstNameCol, lastNameCol);
        membersTable.setItems(membersData);

        pane.setCenter(membersTable);

        return pane;
    }

    private Pane createCompetitorListTab() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 10, 10, 10));

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("lastName"));

        table.setEditable(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                if (event.getClickCount() > 1) {
//                    TableView.TableViewSelectionModel<Member> tableSelectionModel = table.getSelectionModel();
//                    int index = tableSelectionModel.getSelectedIndex();
//                    if (index >= 0) {
//                        Member member = data.get(index);
//
//                        firstNameField.setText(member.getFirstName());
//                        lastNameField.setText(member.getLastName());
//
//                        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
//                        selectionModel.select(0);
//                    }
//                }
            }
        });

        table.getColumns().addAll(firstNameCol, lastNameCol);
        table.setItems(data);

        pane.setCenter(table);

        return pane;
    }

    private Node createButtonPane() {
        final VBox pane = new VBox();
        pane.setSpacing(5);
        pane.setPadding(new Insets(40, 10, 10, 10));

        Button aNew = new Button("New");
        aNew.setMaxWidth(Double.MAX_VALUE);
        aNew.setOnAction(e -> {
//            Member match = new Member(firstNameField.getText(), lastNameField.getText());
//
//            Data.add(match);
//
//            firstNameField.clear();
//            lastNameField.clear();
        });

        Button delete = new Button("Delete");
        delete.setMaxWidth(Double.MAX_VALUE);
        delete.setOnAction(e -> {
            int focusedIndex = table.getSelectionModel().getFocusedIndex();
            if (focusedIndex >= 0)
                data.remove(focusedIndex);
        });

        pane.getChildren().addAll(aNew, delete);
        return pane;
    }
}
