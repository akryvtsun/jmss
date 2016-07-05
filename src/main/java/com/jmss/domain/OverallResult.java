package com.jmss.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OverallResult {
    private final String matchName;
    private final LocalDate matchDate;
    private final List<PersonalResult> items;

    public OverallResult() {
        this.matchName = "Skif Dinamics";
        this.matchDate = LocalDate.now();

        this.items = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            items.add(new PersonalResult(i+1, 12.5, "Shooter 2"));
        }
    }

    public OverallResult(String matchName, LocalDate matchDate, List<PersonalResult> items) {
        this.matchName = matchName;
        this.matchDate = matchDate;
        this.items = items;
    }

    public String getMatchName() {
        return matchName;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public List<PersonalResult> getItems() {
        return items;
    }
}
