package com.jmss.application.views;

import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

public class MembersWindowTest {

    @Before
    public void setUp() throws Exception {
        new JFXPanel();
    }

    @Test
    public void window_creation() throws Exception {
        MembersWindow window = new MembersWindow(Collections.emptyList());
        assertNotNull(window);
    }
}
