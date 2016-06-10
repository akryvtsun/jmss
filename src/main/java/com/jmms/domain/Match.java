package com.jmms.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Match {
    private String name;
    private LocalDate date;
    private List<Stage> stages;

    public Match(String name, LocalDate date) {
        this.name = name;
        this.date = date;
        stages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }
}
