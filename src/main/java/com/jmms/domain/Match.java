package com.jmms.domain;

public final class Match {
    private final String name;
    private final Exercise[] exercises;

    public Match(String name, Exercise[] exercises) {
        this.name = name;
        this.exercises = exercises;
    }
}
