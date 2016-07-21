package com.jmss.infra;

import java.io.InputStream;

public final class Utils {

    public static InputStream getResource(String resourceName) {
        return Utils.class.getClassLoader().getResourceAsStream(resourceName);
    }

    private Utils() {}
}
