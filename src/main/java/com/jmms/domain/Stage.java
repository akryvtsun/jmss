package com.jmms.domain;

public final class Stage {
    private final int number;
    private final int targets;

    public Stage(int number, int targets) {
        this.number = number;
        this.targets = targets;
    }

    public int getNumber() {
        return number;
    }

    public int getTargets() {
        return targets;
    }
}
