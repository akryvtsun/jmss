package com.jmss.infra;

import com.jmss.application.Launcher;

import java.io.InputStream;

public final class Utils {

    public static InputStream getResource(String resourceName) {
        return Utils.class.getResourceAsStream(resourceName);
    }

    private Utils() {}
}
