package com.jmss.application;

import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;

public class CompetitorsWindowTest {

    @Before
    public void setUp() throws Exception {
        new JFXPanel();
    }

    @Test
    public void window_creation() throws Exception {
        CompetitorsWindow window = new CompetitorsWindow(Collections.emptyList(), Collections.emptyList());
        assertNotNull(window);
    }
}
