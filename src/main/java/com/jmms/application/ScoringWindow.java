package com.jmms.application;

import com.jmms.domain.Match;
import com.jmms.domain.Member;
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
import java.util.List;

// TODO add match date label
public class ScoringWindow extends GridPane {

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
        setPadding(new Insets(10, 10, 10, 10));
        setHgap(10);
        setVgap(10);

        matchComboBox = createMatchComboBox(matches);
        stageComboBox = createStageComboBox();
        competitorComboBox = createCompetitorComboBox();

        aHits = createSpinner();
        cHits = createSpinner();
        dHits = createSpinner();

        misses = createSpinner();
        procedurals = createSpinner();

        time = new TextField();

        //matchComboBox.setValue(matches.get(0));


        add(createCoordinatesPane(), 0, 0);
        add(createScoringPane(), 1, 0);

        add(createPenaltiesPane(), 0, 1);
        add(createTimePane(), 1, 1);

        add(createButtonsPane(), 0, 2, 2, 1);
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
        comboBox.setOnAction(event -> {
            Match match = comboBox.getValue();

            List<Stage> stages = match.getStages();
            stageComboBox.setItems(FXCollections.observableList(stages));
            stageComboBox.setValue(stages.get(0));

            List<Member> competitors = match.getCompetitors();
            competitorComboBox.setItems(FXCollections.observableList(competitors));
            competitorComboBox.setValue(competitors.get(0));
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
        comboBox.setOnAction(event -> {
            System.out.println("update spinners");
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
        comboBox.setOnAction(event -> {
            System.out.println("update spinners");
        });
        return comboBox;
    }

    private TitledPane createCoordinatesPane() {

        GridPane pane = new GridPane();
        //pane.setStyle("-fx-background-color: #cccccc; -fx-border-color: #464646; ");
        //pane.setPrefWidth(Double.MAX_VALUE);
        pane.setHgap(5);
        pane.setVgap(5);

        pane.add(new Label("Match:"), 0,0);
        pane.add(matchComboBox, 1,0);

        pane.add(new Label("Stage:"), 0,1);
        pane.add(stageComboBox, 1,1);

        pane.add(new Label("Competitor:"), 0,2);
        pane.add(competitorComboBox, 1,2);

        TitledPane p = new TitledPane("Coordinates", pane);
        p.setCollapsible(false);

        return p;
    }

    private TitledPane createScoringPane() {
        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);
        //pane.setStyle("-fx-background-color: orange; -fx-border-color: #464646; ");

        pane.add(new Label("A Hits:"), 0,0);
        pane.add(aHits, 1,0);

        pane.add(new Label("C Hits:"), 0,1);
        pane.add(cHits, 1,1);

        pane.add(new Label("D Hits:"), 0,2);
        pane.add(dHits, 1,2);

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

        pane.add(new Label("Misses:"), 0,0);
        pane.add(misses, 1,0);

        pane.add(new Label("Procedurals:"), 0,1);
        pane.add(procedurals, 1,1);

        TitledPane p = new TitledPane("Penalties", pane);
        p.setCollapsible(false);
        p.setMaxHeight(Double.MAX_VALUE);

        return p;
    }

    private TitledPane createTimePane() {
        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);

        pane.add(new Label("Total time"), 0,0);
        pane.add(time, 1,0);

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
        pane.setRight(new Button("Confirm"));
        return pane;
    }

    public static void main(String[] args) {
        new JFXPanel();

        Member member1 = new Member("fn1", "ln1");
        Member member2 = new Member("fn2", "ln2");
        Member member3 = new Member("fn3", "ln3");

        Match match1 = new Match("match 1", LocalDate.now());
        match1.getStages().add(new Stage(1, 2));
        match1.getStages().add(new Stage(2, 2));
        match1.getCompetitors().add(member1);
        match1.getCompetitors().add(member2);

        Match match2 = new Match("match 2", LocalDate.now());
        match2.getStages().add(new Stage(1, 3));
        match2.getStages().add(new Stage(2, 3));
        match2.getCompetitors().add(member2);
        match2.getCompetitors().add(member3);

        List<Match> matches = Arrays.asList(match1, match2);
        ScoringWindow window = new ScoringWindow(matches);

        Platform.runLater(new Runnable() {
            @Override public void run() {
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
}
