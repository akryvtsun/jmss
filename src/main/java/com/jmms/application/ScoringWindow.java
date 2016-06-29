package com.jmms.application;

import com.jmms.domain.Match;
import com.jmms.domain.Member;
import com.jmms.domain.Passing;
import com.jmms.domain.Stage;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

// TODO add match date label
// TODO add time number field entering guard
public class ScoringWindow extends GridPane {
    private static final Logger LOG = Logger.getLogger(ScoringWindow.class.getName());

    private final List<Match> matches;

    private ComboBox<Match> matchComboBox;
    private ComboBox<Stage> stageComboBox;
    private ComboBox<Member> competitorComboBox;

    private Spinner<Integer> aHits;
    private Spinner<Integer> cHits;
    private Spinner<Integer> dHits;

    private Spinner<Integer> misses;
    private Spinner<Integer> procedurals;

    private TextField time;

    public ScoringWindow(List<Match> matches) {
        this.matches = matches;

        prepareComponents();

        setPadding(new Insets(10, 10, 10, 10));
        setHgap(10);
        setVgap(10);

        add(createCoordinatesPane(), 0, 0);
        add(createScoringPane(), 1, 0);

        add(createPenaltiesPane(), 0, 1);
        add(createTimePane(), 1, 1);

        add(createButtonsPane(), 0, 2, 2, 1);

        if (!matches.isEmpty()) {
            Match match = matches.get(0);
            matchComboBox.setValue(match);
        }
    }

    private void prepareComponents() {
        matchComboBox = createMatchComboBox(matches);
        stageComboBox = createStageComboBox();
        competitorComboBox = createCompetitorComboBox();

        aHits = createSpinner();
        cHits = createSpinner();
        dHits = createSpinner();

        misses = createSpinner();
        procedurals = createSpinner();

        time = new TextField("0.00");
    }

    private ComboBox<Match> createMatchComboBox(List<Match> matches) {
        ComboBox<Match> comboBox = new ComboBox<>(FXCollections.observableList(matches));
        comboBox.setConverter(new StringConverter<Match>() {
            @Override
            public String toString(Match object) {
                return object.getName();
            }

            @Override
            public Match fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
        comboBox.valueProperty().addListener(e -> {
            LOG.info("Changing match combobox...");

            Match match = comboBox.getValue();

            List<Stage> stages = match.getStages();
            stageComboBox.setItems(FXCollections.observableList(stages));
            stageComboBox.getSelectionModel().select(0);

            List<Member> competitors = match.getCompetitors();
            competitorComboBox.setItems(FXCollections.observableList(competitors));
            competitorComboBox.getSelectionModel().select(0);
        });
        return comboBox;
    }

    private ComboBox<Stage> createStageComboBox() {
        ComboBox<Stage> comboBox = new ComboBox<>();
        comboBox.setConverter(new StringConverter<Stage>() {
            @Override
            public String toString(Stage object) {
                return String.valueOf(object.getNumber());
            }

            @Override
            public Stage fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
        comboBox.valueProperty().addListener(e -> {
            LOG.info("Changing stage combobox...");

            updatePassingComponents();
        });
        return comboBox;
    }

    private ComboBox<Member> createCompetitorComboBox() {
        ComboBox<Member> comboBox = new ComboBox<>();
        comboBox.setConverter(new StringConverter<Member>() {
            @Override
            public String toString(Member object) {
                return String.format("%s, %s", object.getLastName(), object.getFirstName());
            }

            @Override
            public Member fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
        comboBox.valueProperty().addListener(e -> {
            LOG.info("Changing competitor combobox...");

            updatePassingComponents();
        });
        return comboBox;
    }

    private void updatePassingComponents() {
        Match value = matchComboBox.getValue();
        Map<Stage, Map<Member, Passing>> results = value.getResults();

        Stage stage = stageComboBox.getValue();
        Map<Member, Passing> map = results.get(stage);

        if (map == null) {
            map = new HashMap<>();
            results.put(stage, map);
        }

        Member member = competitorComboBox.getValue();
        Passing passing = map.get(member);

        if (passing == null) {
            passing = new Passing(0, 0, 0, 0, 0, 0.0);
            map.put(member, passing);
        }

        aHits.getValueFactory().setValue(passing.getAlphas());
        cHits.getValueFactory().setValue(passing.getCharlies());
        dHits.getValueFactory().setValue(passing.getDeltas());

        misses.getValueFactory().setValue(passing.getMisses());
        procedurals.getValueFactory().setValue(passing.getPenalties());

        // TODO make conversion in "0.00" format
        time.setText(String.valueOf(passing.getTime()));
    }

    private TitledPane createCoordinatesPane() {

        GridPane pane = new GridPane();
        //pane.setStyle("-fx-background-color: #cccccc; -fx-border-color: #464646; ");
        //pane.setPrefWidth(Double.MAX_VALUE);
        pane.setHgap(5);
        pane.setVgap(5);

        pane.add(new Label("Match:"), 0, 0);
        pane.add(matchComboBox, 1, 0);

        pane.add(new Label("Stage:"), 0, 1);
        pane.add(stageComboBox, 1, 1);

        pane.add(new Label("Competitor:"), 0, 2);
        pane.add(competitorComboBox, 1, 2);

        TitledPane p = new TitledPane("Coordinates", pane);
        p.setCollapsible(false);

        return p;
    }

    private TitledPane createScoringPane() {
        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);
        //pane.setStyle("-fx-background-color: orange; -fx-border-color: #464646; ");

        pane.add(new Label("A Hits:"), 0, 0);
        pane.add(aHits, 1, 0);

        pane.add(new Label("C Hits:"), 0, 1);
        pane.add(cHits, 1, 1);

        pane.add(new Label("D Hits:"), 0, 2);
        pane.add(dHits, 1, 2);

        TitledPane p = new TitledPane("Scoring", pane);
        p.setCollapsible(false);
        p.setMaxHeight(Double.MAX_VALUE);

        return p;
    }

    private Node createPenaltiesPane() {
        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);
        //pane.setStyle("-fx-background-color: orange; -fx-border-color: #464646; ");

        pane.add(new Label("Misses:"), 0, 0);
        pane.add(misses, 1, 0);

        pane.add(new Label("Procedurals:"), 0, 1);
        pane.add(procedurals, 1, 1);

        TitledPane p = new TitledPane("Penalties", pane);
        p.setCollapsible(false);
        p.setMaxHeight(Double.MAX_VALUE);

        return p;
    }

    private TitledPane createTimePane() {
        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);

        pane.add(new Label("Total time"), 0, 0);
        pane.add(time, 1, 0);

        TitledPane p = new TitledPane("Time", pane);
        p.setCollapsible(false);
        p.setMaxHeight(Double.MAX_VALUE);

        return p;
    }

    private Spinner createSpinner() {
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setEditable(true);
        SpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99);
        spinner.setValueFactory(svf);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        return spinner;
    }

    private Pane createButtonsPane() {
        BorderPane pane = new BorderPane();
        Button button = new Button("Confirm");
        button.setOnAction(event -> {
            LOG.info("Confirming score...");

            Passing passing = new Passing(aHits.getValue(), cHits.getValue(), dHits.getValue(),
                    misses.getValue(), procedurals.getValue(),
                    Double.parseDouble(time.getText()));

            Match match = matchComboBox.getValue();
            Map<Member, Passing> map = match.getResults().get(stageComboBox.getValue());

            if (map == null) {
                map = new HashMap<>();
                match.getResults().put(stageComboBox.getValue(), map);
            }

            map.put(competitorComboBox.getValue(), passing);
        });
        pane.setRight(button);
        return pane;
    }
}
