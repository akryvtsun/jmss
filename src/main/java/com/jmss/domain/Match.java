package com.jmss.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Match {
    private final String name;
    private final LocalDate date;
    private final List<Stage> stages;
    private final List<Member> competitors;
    private final Map<Stage, Map<Member, Passing>> results;

    public Match(String name, LocalDate date) {
        this.name = name;
        this.date = date;
        stages = new ArrayList<>();
        competitors = new ArrayList<>();
        results = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public List<Member> getCompetitors() {
        return competitors;
    }

    public Map<Stage, Map<Member, Passing>> getResults() {
        return results;
    }
}
