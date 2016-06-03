package com.jmms.domain;

import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MatchTest {

    @Test
    public void match_creation() throws Exception {
        Match demo = new Match("demo", new Exercise[]{
                new Exercise(1, 2),
                new Exercise(2, 2),
                new Exercise(3, 2)
        });

        assertNotNull(demo);
    }

    @Test
    public void match_calculation() throws Exception {
        Shooter A = new Shooter("A", "A");
        Shooter B = new Shooter("B", "B");

        Exercise exercise = new Exercise(1, 2);

        Match demo = new Match("demo", new Exercise[]{
                exercise
        });
        demo.add(exercise, A, new Passing(2, 0, 0, 0, 0, 10.5));
        demo.add(exercise, B, new Passing(0, 1, 1, 0, 0, 5.5));

        Map<Shooter, Double> result = demo.overall();

        assertEquals(10.5, result.get(A));
        assertEquals(11.5, result.get(B));
    }
}
