package com.jmss.domain;

import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OldMatchTest {

    @Test
    public void match_creation() throws Exception {
        OldMatch demo = new OldMatch("demo", new Stage[]{
                new Stage(1, 2),
                new Stage(2, 2),
                new Stage(3, 2)
        });

        assertNotNull(demo);
    }

    @Test
    public void match_calculation() throws Exception {
        Member A = new Member("A", "A");
        Member B = new Member("B", "B");

        Stage stage = new Stage(1, 2);

        OldMatch demo = new OldMatch("demo", new Stage[]{
                stage
        });
        demo.add(stage, A, new Passing(2, 0, 0, 0, 0, 10.5));
        demo.add(stage, B, new Passing(0, 1, 1, 0, 0, 5.5));

        Map<Member, Double> result = demo.overall();

        assertEquals(10.5, result.get(A));
        assertEquals(11.5, result.get(B));
    }
}
