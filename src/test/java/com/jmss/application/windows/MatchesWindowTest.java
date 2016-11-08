package com.jmss.application.windows;

import static org.junit.Assert.assertNotNull;

import com.jmss.domain.Database;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

public class MatchesWindowTest {

    @Before
    public void setUp() throws Exception {
        new JFXPanel();
    }

    @Test
    public void window_creation() throws Exception {
        MatchesWindow window = new MatchesWindow(new Database());
        assertNotNull(window);
    }
}
