package com.jmss.application;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO use javafxpackager.exe for runnable package creation
public final class Launcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        LOGGER.info("Starting application...");
        Application.launch(JmssApplication.class, args);
        LOGGER.info("Application was stopped!");
    }
}
