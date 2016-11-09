package com.jmss.application.views;

import java.util.List;

import com.jmss.domain.Member;
import com.jmss.domain.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO add number fields (stage No) protection (spinner?)
// TODO allows correct stages attrs changes
public class StagesView extends BorderPane {
    private static final Logger LOGGER = LoggerFactory.getLogger(StagesView.class);

    private final ObservableList<Stage> data;

    private final TabPane tabPane = new TabPane();

    private final TextField numberField = new TextField();
    private final TextField targetsField = new TextField();

    private final TableView<Stage> table = new TableView<>();

    public StagesView(List<Stage> stages) {
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
                (observable, oldValue, newValue) -> {
                    LOGGER.debug("Changing tab to '{}'...", newValue.getText());

                    TableView.TableViewSelectionModel<Stage> tableSelectionModel = table.getSelectionModel();
                    int index = tableSelectionModel.getSelectedIndex();
                    if (index >= 0) {
                        if (memberListTab.equals(newValue)) {
                            LOGGER.trace("Updating stage in table...");

                            String number = numberField.getText();
                            String targets = targetsField.getText();
                            Stage stage = new Stage(Integer.valueOf(number), Integer.valueOf(targets));

                            data.set(index, stage);
                        } else if (memberTab.equals(newValue)) {
                            LOGGER.trace("Updating stage's fields...");

                            Stage stage = data.get(index);

                            numberField.setText(String.valueOf(stage.getNumber()));
                            targetsField.setText(String.valueOf(stage.getTargets()));
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
        table.setOnMouseClicked(event -> {
            LOGGER.trace("Updating stage's fields via mouse...");

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
            LOGGER.debug("Adding new stage...");

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
            LOGGER.debug("Deleting stage...");

            int focusedIndex = table.getSelectionModel().getFocusedIndex();
            if (focusedIndex >= 0)
                data.remove(focusedIndex);
        });

        pane.getChildren().addAll(aNew, delete);
        return pane;
    }
}
