package com.jmms.domain;

public final class Stage {
    private int number;
    private int targets;

    public Stage(int number, int targets) {
        this.number = number;
        this.targets = targets;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTargets() {
        return targets;
    }

    public void setTargets(int targets) {
        this.targets = targets;
    }
}
