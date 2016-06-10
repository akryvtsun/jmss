package com.jmms.application;

import com.jmms.domain.Match;
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

// TODO disable Delete button if table is empty
// TODO disable make whole window smaller (or even remove resize ability at all)
public class MatchesWindow extends BorderPane {

    static ObservableList<Match> Data = FXCollections.observableArrayList();

    private final TabPane tabPane = new TabPane();

    private final TextField matchNameField = new TextField();
    private final DatePicker dateField = new DatePicker();

    private final TableView<Match> table = new TableView<>();

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
        Tab matchTab = new Tab("Match");
        matchTab.setContent(createMatchTab());

        Tab matchListTab = new Tab("Match List");
        matchListTab.setContent(createMatchListTab());

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                        TableView.TableViewSelectionModel<Match> tableSelectionModel = table.getSelectionModel();
                        int index = tableSelectionModel.getSelectedIndex();
                        if (index >= 0) {
                            if (matchListTab.equals(newValue)) {
                                Match match = new Match(matchNameField.getText(), dateField.getValue());

                                Data.set(index, match);
                            } else if (matchTab.equals(newValue)) {
                                Match match = Data.get(index);

                                matchNameField.setText(match.getName());
                                // TODO make field clean here
                                dateField.setValue(match.getDate());
                            }
                        }
                    }
                }
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().add(matchTab);
        tabPane.getTabs().add(matchListTab);

        return tabPane;
    }

    private Pane createMatchTab() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        Label label1 = new Label("Match Name:");
        label1.setMinWidth(Control.USE_PREF_SIZE);
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
        TableColumn nameCol = new TableColumn("Match Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Match, String>("name"));

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<Match, LocalDate>("date"));

        table.setEditable(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    TableView.TableViewSelectionModel<Match> tableSelectionModel = table.getSelectionModel();
                    int index = tableSelectionModel.getSelectedIndex();
                    if (index >= 0) {
                        Match match = Data.get(index);

                        dateField.setValue(match.getDate());
                        matchNameField.setText(match.getName());

                        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                        selectionModel.select(0);
                    }
                }
            }
        });

        table.setItems(Data);
        table.getColumns().addAll(dateCol, nameCol);

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

            Data.add(match);

            matchNameField.clear();
            dateField.setValue(LocalDate.now());
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
