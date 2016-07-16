package com.jmss.application.windows;

import com.jmss.application.windows.MatchesWindow;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;

public class MatchesWindowTest {

    @Before
    public void setUp() throws Exception {
        new JFXPanel();
    }

    @Test
    public void window_creation() throws Exception {
        MatchesWindow window = new MatchesWindow(Collections.emptyList(), Collections.emptyList());
        assertNotNull(window);
    }
}
