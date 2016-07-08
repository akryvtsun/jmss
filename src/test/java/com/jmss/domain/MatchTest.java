package com.jmss.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class MatchTest {

    @Test
    public void match_overall_calculation() {
        Member A = new Member("A", "A");
        Member B = new Member("B", "B");

        Stage stage = new Stage(1, 2);

        Match demo = new Match("demo", LocalDate.now());
        demo.getCompetitors().add(A);
        demo.getCompetitors().add(B);

        demo.getStages().add(stage);

        Map<Member, Passing> results = new HashMap<>();
        results.put(A, new Passing(2, 0, 0, 0, 0, 10.5));
        results.put(B, new Passing(0, 1, 1, 0, 0, 5.5));

        demo.getResults().put(stage, results);

        Map<Member, Double> result = demo.overall();

        assertEquals(10.5, result.get(A));
        assertEquals(11.5, result.get(B));
    }

    @Test
    public void match_stages_calculation() {
        Match demo = new Match("demo", LocalDate.now());

        //// members
        Member A = new Member("A", "A");
        Member B = new Member("B", "B");

        demo.getCompetitors().add(A);
        demo.getCompetitors().add(B);

        //// stages
        Stage stage1 = new Stage(1, 2);
        Stage stage2 = new Stage(2, 2);

        demo.getStages().add(stage1);
        demo.getStages().add(stage2);

        //// stage1 results
        Map<Member, Passing> results1 = new HashMap<>();
        results1.put(A, new Passing(2, 0, 0, 0, 0, 10.5));
        results1.put(B, new Passing(0, 1, 1, 0, 0, 5.5));

        demo.getResults().put(stage1, results1);

        //// stage2 results
        Map<Member, Passing> results2 = new HashMap<>();
        results2.put(A, new Passing(0, 1, 1, 0, 0, 5.5));
        results2.put(B, new Passing(2, 0, 0, 0, 0, 10.5));

        demo.getResults().put(stage2, results2);

        //// check 1
        Map<Member, Double> stageResult1 = demo.result(stage1);

        assertEquals(10.5, stageResult1.get(A));
        assertEquals(11.5, stageResult1.get(B));

        //// check 2
        Map<Member, Double> stageResult2 = demo.result(stage2);

        assertEquals(11.5, stageResult2.get(A));
        assertEquals(10.5, stageResult2.get(B));
    }
}
