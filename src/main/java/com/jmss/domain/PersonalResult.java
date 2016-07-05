package com.jmss.domain;

public class PersonalResult {
    private final int number;
    private final double time;
    private final String name;

    public PersonalResult(int number, double time, String name) {
        this.number = number;
        this.time = time;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public double getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
