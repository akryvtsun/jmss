package com.jmss.application;

import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggableStage extends Stage {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableStage.class);

    public LoggableStage(String title) {
        setTitle(title);
        setOnShowing(e -> LOGGER.info("'{}' is opening...", getTitle()));
        setOnCloseRequest(e -> LOGGER.info("'{}' has closed!", getTitle()));
    }
}
