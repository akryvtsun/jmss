package com.jmms.domain;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public final class OldMatch {
    private final String name;
    private final Stage[] stages;
    private final Map<Stage, Map<Member, Passing>> results;

    public OldMatch(String name, Stage[] stages) {
        this.name = name;
        this.stages = stages;
        results = new HashMap<>();
    }

    public void add(Stage stage, Member participant, Passing result) {
        Map<Member, Passing> passingMap = results.get(stage);
        if (passingMap == null) {
            passingMap = new HashMap<>();
            results.put(stage, passingMap);
        }
        passingMap.put(participant, result);
    }

    public Map<Member, Double> result(Stage stage) {
        Map<Member, Passing> passingMap = results.get(stage);
        Map<Member, Double> result = new HashMap<>();
        for (Map.Entry<Member, Passing> entry: passingMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().result());
        }
        return result;
    }

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
