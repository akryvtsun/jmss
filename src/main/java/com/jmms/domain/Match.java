package com.jmms.domain;

import java.util.HashMap;
import java.util.Map;

public final class Match {
    private final String name;
    private final Stage[] stages;
    private final Map<Stage, Map<Shooter, Passing>> results;

    public Match(String name, Stage[] stages) {
        this.name = name;
        this.stages = stages;
        results = new HashMap<>();
    }

    public void add(Stage stage, Shooter participant, Passing result) {
        Map<Shooter, Passing> passingMap = results.get(stage);
        if (passingMap == null) {
            passingMap = new HashMap<>();
            results.put(stage, passingMap);
        }
        passingMap.put(participant, result);
    }

    public Map<Shooter, Double> result(Stage stage) {
        Map<Shooter, Passing> passingMap = results.get(stage);
        Map<Shooter, Double> result = new HashMap<>();
        for (Map.Entry<Shooter, Passing> entry: passingMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().result());
        }
        return result;
    }

    public Map<Shooter, Double> overall() {
        Map<Shooter, Double> result = new HashMap<>();
        for (Stage stage : stages) {
            Map<Shooter, Double> map = result(stage);
            for (Map.Entry<Shooter, Double> entry: map.entrySet()) {
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
