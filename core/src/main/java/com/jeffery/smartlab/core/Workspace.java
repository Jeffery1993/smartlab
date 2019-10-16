package com.jeffery.smartlab.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class Workspace {

    private static final Map<String, Object> ENV = new LinkedHashMap<>();

    public static Map<String, Object> getEnv() {
        return ENV;
    }

}
