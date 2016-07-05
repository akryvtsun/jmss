package com.jmss.domain;

public final class Passing {
    private final int alphas, charlies, deltas;
    private final int misses, penalties;
    private final double time;

    public Passing(int alphas, int charlies, int deltas, int misses, int penalties, double time) {
        this.alphas = alphas;
        this.charlies = charlies;
        this.deltas = deltas;
        this.misses = misses;
        this.penalties = penalties;
        this.time = time;
    }

    public int getAlphas() {
        return alphas;
    }

    public int getCharlies() {
        return charlies;
    }

    public int getDeltas() {
        return deltas;
    }

    public int getMisses() {
        return misses;
    }

    public int getPenalties() {
        return penalties;
    }

    public double getTime() {
        return time;
    }

    public double result() {
        return charlies * 2 + deltas * 4 + misses * 10 + penalties * 10 + time;
    }
}
