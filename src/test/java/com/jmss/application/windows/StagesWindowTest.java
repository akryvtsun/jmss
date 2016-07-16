package com.jmss.application.windows;

import com.jmss.application.windows.StagesWindow;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;

public class StagesWindowTest {

    @Before
    public void setUp() throws Exception {
        new JFXPanel();
    }

    @Test
    public void window_creation() throws Exception {
        StagesWindow window = new StagesWindow(Collections.emptyList());
        assertNotNull(window);
    }
}
