package com.jmss.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class MatchTest {

    @Test
    public void match_calculation() throws Exception {
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
}
