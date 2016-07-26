package com.jmss.application.windows;

import com.jmss.domain.Match;
import com.jmss.domain.Member;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

// TODO disable Delete button if table is empty
// TODO disable make whole window smaller (or even remove resize ability at all)
// TODO disable toolbar buttons if no match was selected
// TODO allows correct match data changes
public class MatchesWindow extends BorderPane {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchesWindow.class);

    private final List<Member> members;
    private final ObservableList<Match> data;

    private final TabPane tabPane = new TabPane();

    private final TextField matchNameField = new TextField();
    private final DatePicker dateField = new DatePicker();

    private final TableView<Match> table = new TableView<>();

    public MatchesWindow(List<Member> members, List<Match> matches) {
        this.members = members;
        data = FXCollections.observableList(matches);

        setTop(createToolBarPane());
        setCenter(createTabPane());
        setRight(createButtonPane());
    }

    private ToolBar createToolBarPane() {
        ToolBar toolBar = new ToolBar();

        Button stages = createStagesButton();
        Button competitors = createCompetitorsButton();

        toolBar.getItems().addAll(stages, competitors);
        return toolBar;
    }

    private Button createStagesButton() {
        Button button = new Button("Stages");
        ImageView value = new ImageView("/icons/stages.png");
        value.setFitHeight(50);
        value.setFitWidth(50);
        button.setGraphic(value);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            LOGGER.info("Stage Administration opening...");

            Stage stage = new Stage();
            stage.setTitle("Stage Administration");

            TableView.TableViewSelectionModel<Match> tableSelectionModel = table.getSelectionModel();
            int index = tableSelectionModel.getSelectedIndex();
            if (index >= 0) {
                Match match = data.get(index);

                Scene scene = new Scene(new StagesWindow(match.getStages()));
                stage.setScene(scene);
                // TODO make centering
                //centerStage(stage, stage.getWidth(), stage.getHeight());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
        });
        return button;
    }

    private Button createCompetitorsButton() {
        Button button = new Button("Competitors");
        ImageView value = new ImageView("/icons/competitors.png");
        value.setFitHeight(50);
        value.setFitWidth(50);
        button.setGraphic(value);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(e -> {
            LOGGER.info("Competitors Administration opening...");

            Stage stage = new Stage();
            stage.setTitle("Competitors Administration");

            TableView.TableViewSelectionModel<Match> tableSelectionModel = table.getSelectionModel();
            int index = tableSelectionModel.getSelectedIndex();
            if (index >= 0) {
                Match match = data.get(index);
                List<Member> competitors = match.getCompetitors();

                Scene scene = new Scene(new CompetitorsWindow(members, competitors));
                stage.setScene(scene);
                // TODO make centering
                //centerStage(stage, stage.getWidth(), stage.getHeight());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
        });
        return button;
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
                        LOGGER.info("Changing Matches tab...");

                        TableView.TableViewSelectionModel<Match> tableSelectionModel = table.getSelectionModel();
                        int index = tableSelectionModel.getSelectedIndex();
                        if (index >= 0) {
                            if (matchListTab.equals(newValue)) {
                                LOGGER.info("Updating match in table...");

                                Match match = new Match(matchNameField.getText(), dateField.getValue());

                                data.set(index, match);
                            } else if (matchTab.equals(newValue)) {
                                LOGGER.info("Updating match's fields...");

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
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<Match, LocalDate>("date"));

        TableColumn nameCol = new TableColumn("Match Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Match, String>("name"));

        table.setEditable(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LOGGER.info("Updating match's fields via mouse...");

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
            LOGGER.info("Adding new match...");

            Match match = new Match(matchNameField.getText(), dateField.getValue());

            data.add(match);

            matchNameField.clear();
            dateField.setValue(null);
        });

        Button delete = new Button("Delete");
        delete.setMaxWidth(Double.MAX_VALUE);
        delete.setOnAction(e -> {
            LOGGER.info("Deleting match...");

            int focusedIndex = table.getSelectionModel().getFocusedIndex();
            if (focusedIndex >= 0)
                data.remove(focusedIndex);
        });

        pane.getChildren().addAll(aNew, delete);
        return pane;
    }
}
