package com.jmms.application;

import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.time.LocalDate;

public class MatchesWindow extends BorderPane {

    final static ObservableList<Match> data = FXCollections.observableArrayList();

    final TabPane tabPane = new TabPane();

    final TextField matchNameField = new TextField();
    final DatePicker dateField = new DatePicker();

    TableView<Match> table = new TableView<>();

    public MatchesWindow() {
        setTop(createToolBarPane());
        setCenter(createTabPane());
        setRight(createButtonPane());
    }

    private ToolBar createToolBarPane() {
        ToolBar toolBar = new ToolBar();

        Button stages = new Button("Stages");
        stages.setGraphic(new ImageView("/stages.png"));
        stages.setContentDisplay(ContentDisplay.TOP);

        Button competitors = new Button("Competitors");
        competitors.setGraphic(new ImageView("/competitors.png"));
        competitors.setContentDisplay(ContentDisplay.TOP);

        toolBar.getItems().addAll(stages, competitors);
        return toolBar;
    }

    private TabPane createTabPane() {
        Tab memberTab = new Tab("Match");
        memberTab.setContent(createMatchTab());

        Tab membersTab = new Tab("Match List");
        membersTab.setContent(createMatchListTab());

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        TableView.TableViewSelectionModel<Match> tableSelectionModel = table.getSelectionModel();
                        int index = tableSelectionModel.getSelectedIndex();
                        if (index >= 0) {
                            if ("Match List".equals(t1.getText())) {
                                Match match = new Match(matchNameField.getText(), dateField.getValue());

                                data.set(index, match);
                            } else if ("Match".equals(t1.getText())) {
                                Match match = data.get(index);

                                matchNameField.setText(match.getName());
                                // TODO make field clean here
                                dateField.setValue(match.getDate());
                            }
                        }
                    }
                }
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().add(memberTab);
        tabPane.getTabs().add(membersTab);

        return tabPane;
    }

    private Pane createMatchTab() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        Label label1 = new Label("Match Name:");
        GridPane.setConstraints(label1, 0, 0);
        pane.getChildren().add(label1);

        GridPane.setConstraints(matchNameField, 1, 0);
        pane.getChildren().add(matchNameField);

        Label label2 = new Label("Date:");
        GridPane.setConstraints(label2, 0, 1);
        pane.getChildren().add(label2);

        GridPane.setConstraints(dateField, 1, 1);
        pane.getChildren().add(dateField);

        return pane;
    }

    private Pane createMatchListTab() {
        TableColumn firstNameCol = new TableColumn("Date");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Match, LocalDate>("date"));

        TableColumn lastNameCol = new TableColumn("Match Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Match, String>("name"));

        table.setEditable(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    TableView.TableViewSelectionModel<Match> tableSelectionModel = table.getSelectionModel();
                    int index = tableSelectionModel.getSelectedIndex();
                    if (index >= 0) {
                        Match match = data.get(index);

                        dateField.setValue(match.getDate());
                        matchNameField.setText(match.getName());

                        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                        selectionModel.select(0);
                    }
                }
            }
        });

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol);

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
            Match match = new Match(matchNameField.getText(), dateField.getValue());

            data.add(match);

            matchNameField.clear();
            dateField.setValue(LocalDate.now());
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

    public static class Match {
        private final SimpleStringProperty name;
        private final SimpleObjectProperty<LocalDate> date;

        private Match(String name, LocalDate date) {
            this.name = new SimpleStringProperty(name);
            this.date = new SimpleObjectProperty<>(date);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public LocalDate getDate() {
            return date.get();
        }

        public void setDate(LocalDate date) {
            this.date.set(date);
        }
    }
}
