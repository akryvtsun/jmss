package com.jmms.domain;

import java.util.HashMap;
import java.util.Map;

public final class Match {
    private final String name;
    private final Exercise[] exercises;
    private final Map<Exercise, Map<Shooter, Passing>> results;

    public Match(String name, Exercise[] exercises) {
        this.name = name;
        this.exercises = exercises;
        results = new HashMap<>();
    }

    public void add(Exercise exercise, Shooter participant, Passing result) {
        Map<Shooter, Passing> passingMap = results.get(exercise);
        if (passingMap == null) {
            passingMap = new HashMap<>();
            results.put(exercise, passingMap);
        }
        passingMap.put(participant, result);
    }

    public Map<Shooter, Double> result(Exercise exercise) {
        Map<Shooter, Passing> passingMap = results.get(exercise);
        Map<Shooter, Double> result = new HashMap<>();
        for (Map.Entry<Shooter, Passing> entry: passingMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().result());
        }
        return result;
    }

    public Map<Shooter, Double> overall() {
        Map<Shooter, Double> result = new HashMap<>();
        for (Exercise exercise: exercises) {
            Map<Shooter, Double> map = result(exercise);
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
