package com.jmms.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Match {
    private final String name;
    private final LocalDate date;
    private final List<Stage> stages;
    private final List<Member> competitors;

    public Match(String name, LocalDate date) {
        this.name = name;
        this.date = date;
        stages = new ArrayList<>();
        competitors = new ArrayList<>();
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
}
