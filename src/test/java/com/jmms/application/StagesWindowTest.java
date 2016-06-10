package com.jmms.application;

import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class StagesWindowTest {

    @Before
    public void setUp() throws Exception {
        new JFXPanel();
    }

    @Test
    public void window_creation() throws Exception {
        StagesWindow window = new StagesWindow(new ArrayList<>());
        assertNotNull(window);
    }
}
