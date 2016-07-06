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

    // TODO add unit test
    public Map<Member, Double> result(Stage stage) {
        Map<Member, Passing> passingMap = results.get(stage);
        Map<Member, Double> result = new HashMap<>();
        for (Map.Entry<Member, Passing> entry: passingMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().result());
        }
        return result;
    }

    // TODO add unit test
    public Map<Member, Double> overall() {
        Map<Member, Double> result = new HashMap<>();
        for (Stage stage : stages) {
            Map<Member, Double> map = result(stage);
            for (Map.Entry<Member, Double> entry: map.entrySet()) {
                Double aDouble = result.get(entry.getKey());
                if (aDouble == null) {
                    aDouble = Double.valueOf(0);
                }
                result.put(entry.getKey(), aDouble + entry.getValue());
            }
        }
        return result;
    }
}
