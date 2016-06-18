package com.jmms.application;

import com.jmms.domain.Match;
import com.jmms.domain.Member;
import com.jmms.domain.Passing;
import com.jmms.domain.Stage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO add match date label
// TODO add time number field entering guard
public class ScoringWindow extends GridPane {

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

    public static void main(String[] args) {
        new JFXPanel();

        Member member1 = new Member("fn1", "ln1");
        Member member2 = new Member("fn2", "ln2");
        Member member3 = new Member("fn3", "ln3");

        ///////////////////

        Stage stage11 = new Stage(1, 2);
        Stage stage12 = new Stage(2, 2);

        Match match1 = new Match("match 1", LocalDate.now());
        match1.getStages().add(stage11);
        match1.getStages().add(stage12);
        match1.getCompetitors().add(member1);
        match1.getCompetitors().add(member2);

        Map<Member, Passing> map11 = new HashMap<>();
        map11.put(member1, new Passing(3, 0, 1, 1, 0, 111.0));
        map11.put(member2, new Passing(2, 1, 3, 1, 0, 112.0));
        match1.getResults().put(stage11, map11);

        Map<Member, Passing> map12 = new HashMap<>();
        map12.put(member1, new Passing(1, 2, 0, 0, 2, 121.0));
        map12.put(member2, new Passing(0, 4, 1, 2, 0, 122.0));
        match1.getResults().put(stage12, map12);

        //////////////////

        Stage stage21 = new Stage(1, 3);
        Stage stage22 = new Stage(2, 3);

        Match match2 = new Match("match 2", LocalDate.now());
        match2.getStages().add(stage21);
        match2.getStages().add(stage22);
        match2.getCompetitors().add(member2);
        match2.getCompetitors().add(member3);

        Map<Member, Passing> map21 = new HashMap<>();
        map21.put(member2, new Passing(3, 0, 1, 1, 0, 211.0));
        map21.put(member3, new Passing(4, 1, 1, 0, 2, 212.0));
        match2.getResults().put(stage21, map21);

        Map<Member, Passing> map22 = new HashMap<>();
        map22.put(member2, new Passing(1, 2, 0, 0, 2, 221.0));
        map22.put(member3, new Passing(3, 0, 2, 1, 0, 222.0));
        match2.getResults().put(stage22, map22);

        //////////////////

        List<Match> matches = Arrays.asList(match1, match2);
        ScoringWindow window = new ScoringWindow(matches);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setTitle("Rapid Scoring");
                Scene scene = new Scene(window);
                stage.setScene(scene);
                // TODO make centering
                //centerStage(stage, stage.getWidth(), stage.getHeight());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
        });
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
        SpinnerValueFactory svf =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99);
        spinner.setValueFactory(svf);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        return spinner;
    }

    private Pane createButtonsPane() {
        BorderPane pane = new BorderPane();
        Button button = new Button("Confirm");
        button.setOnAction(event -> {
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
