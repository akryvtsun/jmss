package com.jmss.infra.results;

import com.jmss.domain.Member;

final class CompetitorRecord {
    private final int number;
    private final double time;
    private final Member competitor;

    public CompetitorRecord(int number, double time, Member competitor) {
        this.number = number;
        this.time = time;
        this.competitor = competitor;
    }

    public int getNumber() {
        return number;
    }

    public double getTime() {
        return time;
    }

    public Member getCompetitor() {
        return competitor;
    }
}
