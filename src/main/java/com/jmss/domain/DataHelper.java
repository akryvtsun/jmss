package com.jmss.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHelper {

    public static List<Member> createMembers() {
        return Arrays.asList(
                new Member("Boris", "Petrov"),
                new Member("Andrey", "Ivanov"),
                new Member("Petr", "Sidorov"),
                new Member("Денис", "Безвершенко")
        );
    }

    public static List<Match> createMatches(List<Member> members) {

        ///////////////////

        Stage stage11 = new Stage(1, 2);
        Stage stage12 = new Stage(2, 2);

        Match match1 = new Match("Skif Dynamics", LocalDate.now());
        match1.getStages().add(stage11);
        match1.getStages().add(stage12);
        match1.getCompetitors().add(members.get(0));
        match1.getCompetitors().add(members.get(1));
        match1.getCompetitors().add(members.get(3));

        Map<Member, Passing> map11 = new HashMap<>();
        map11.put(members.get(0), new Passing(3, 0, 1, 1, 0, 111.0));
        map11.put(members.get(1), new Passing(2, 1, 3, 1, 0, 112.0));
        map11.put(members.get(3), new Passing(4, 0, 0, 0, 0, 100.0));
        match1.getResults().put(stage11, map11);

        Map<Member, Passing> map12 = new HashMap<>();
        map12.put(members.get(0), new Passing(1, 2, 0, 0, 2, 121.0));
        map12.put(members.get(1), new Passing(0, 4, 1, 2, 0, 122.0));
        map12.put(members.get(3), new Passing(6, 0, 1, 0, 2, 155.0));
        match1.getResults().put(stage12, map12);

        //////////////////

        Stage stage21 = new Stage(1, 3);
        Stage stage22 = new Stage(2, 3);

        Match match2 = new Match("Benelli Cup", LocalDate.now());
        match2.getStages().add(stage21);
        match2.getStages().add(stage22);
        match2.getCompetitors().add(members.get(1));
        match2.getCompetitors().add(members.get(3));

        Map<Member, Passing> map21 = new HashMap<>();
        map21.put(members.get(1), new Passing(3, 0, 1, 1, 0, 211.0));
        map21.put(members.get(3), new Passing(4, 1, 1, 0, 2, 212.0));
        match2.getResults().put(stage21, map21);

        Map<Member, Passing> map22 = new HashMap<>();
        map22.put(members.get(1), new Passing(1, 2, 0, 0, 2, 221.0));
        map22.put(members.get(3), new Passing(3, 0, 2, 1, 0, 222.0));
        match2.getResults().put(stage22, map22);

        //////////////////

        return Arrays.asList(match1, match2);
    }
}
