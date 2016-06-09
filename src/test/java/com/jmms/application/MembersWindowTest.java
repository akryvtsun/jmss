package com.jmms.application;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MembersWindowTest {

    @Test
    @Ignore
    public void window_creation() throws Exception {
        MembersWindow window = new MembersWindow();
        assertNotNull(window);
    }
}
