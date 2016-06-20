package com.jmms.application;

import com.jmms.domain.Member;
import com.jmms.domain.Stage;
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

import java.util.List;
import java.util.logging.Logger;

// TODO add number fields (stage No) protection (spinner?)
// TODO allows correct stages attrs changes
public class StagesWindow extends BorderPane {
    private static final Logger LOG = Logger.getLogger(StagesWindow.class.getName());

    private final ObservableList<Stage> data;

    private final TabPane tabPane = new TabPane();

    private final TextField numberField = new TextField();
    private final TextField targetsField = new TextField();

    private final TableView<Stage> table = new TableView<>();

    public StagesWindow(List<Stage> stages) {
        data = FXCollections.observableList(stages);

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
                        LOG.info("Changing Stage tab...");

                        TableView.TableViewSelectionModel<Stage> tableSelectionModel = table.getSelectionModel();
                        int index = tableSelectionModel.getSelectedIndex();
                        if (index >= 0) {
                            if (memberListTab.equals(newValue)) {
                                LOG.info("Updating stage in table...");

                                String number = numberField.getText();
                                String targets = targetsField.getText();
                                Stage stage = new Stage(Integer.valueOf(number), Integer.valueOf(targets));

                                data.set(index, stage);
                            } else if (memberTab.equals(newValue)) {
                                LOG.info("Updating stage's fields...");

                                Stage stage = data.get(index);

                                numberField.setText(String.valueOf(stage.getNumber()));
                                targetsField.setText(String.valueOf(stage.getTargets()));
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

        Label label1 = new Label("Number:");
        GridPane.setConstraints(label1, 0, 0);
        pane.getChildren().add(label1);

        GridPane.setConstraints(numberField, 1, 0);
        pane.getChildren().add(numberField);

        Label label2 = new Label("Targets:");
        GridPane.setConstraints(label2, 0, 1);
        pane.getChildren().add(label2);

        GridPane.setConstraints(targetsField, 1, 1);
        pane.getChildren().add(targetsField);

        return pane;
    }

    private Pane createStagesListTab() {
        TableColumn numberCol = new TableColumn("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<Member, String>("number"));

        TableColumn targetsCol = new TableColumn("Targets");
        targetsCol.setCellValueFactory(new PropertyValueFactory<Member, String>("targets"));

        table.setEditable(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LOG.info("Updating stage's fields via mouse...");

                if (event.getClickCount() > 1) {
                    TableView.TableViewSelectionModel<Stage> tableSelectionModel = table.getSelectionModel();
                    int index = tableSelectionModel.getSelectedIndex();
                    if (index >= 0) {
                        Stage stage = data.get(index);

                        numberField.setText(String.valueOf(stage.getNumber()));
                        targetsField.setText(String.valueOf(stage.getTargets()));

                        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                        selectionModel.select(0);
                    }
                }
            }
        });

        table.setItems(data);
        table.getColumns().addAll(numberCol, targetsCol);

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
            LOG.info("Adding new stage...");

            String number = numberField.getText();
            String targets = targetsField.getText();
            Stage stage = new Stage(Integer.valueOf(number), Integer.valueOf(targets));

            data.add(stage);

            numberField.clear();
            targetsField.clear();
        });

        Button delete = new Button("Delete");
        delete.setMaxWidth(Double.MAX_VALUE);
        delete.setOnAction(e -> {
            LOG.info("Deleting stage...");

            int focusedIndex = table.getSelectionModel().getFocusedIndex();
            if (focusedIndex >= 0)
                data.remove(focusedIndex);
        });

        pane.getChildren().addAll(aNew, delete);
        return pane;
    }
}
