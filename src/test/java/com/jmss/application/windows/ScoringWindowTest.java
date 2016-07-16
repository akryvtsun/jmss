package com.jmss.application.windows;

import com.jmss.application.windows.ScoringWindow;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;

public class ScoringWindowTest {

    @Before
    public void setUp() throws Exception {
        new JFXPanel();
    }

    @Test
    public void window_creation() throws Exception {
        ScoringWindow window = new ScoringWindow(Collections.emptyList());
        assertNotNull(window);
    }
}
