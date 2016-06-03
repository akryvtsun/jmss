package com.jmms.domain;

import org.junit.Test;

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
}
