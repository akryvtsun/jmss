package com.jmms.application;

import com.jmms.domain.Match;
import com.jmms.domain.Member;
import com.jmms.domain.Passing;
import com.jmms.domain.Stage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ax01220 on 6/29/2016.
 */
public class DataHelper {

    public static List<Match> createMatches() {
        Member member1 = new Member("fn1", "ln1");
        Member member2 = new Member("fn2", "ln2");
        Member member3 = new Member("fn3", "ln3");

        ///////////////////

        Stage stage11 = new Stage(1, 2);
        Stage stage12 = new Stage(2, 2);

        Match match1 = new Match("match 1", LocalDate.now());
        match1.getStages().add(stage11);
        match1.getStages().add(stage12);
        match1.getCompetitors().add(member1);
        match1.getCompetitors().add(member2);

        Map<Member, Passing> map11 = new HashMap<>();
        map11.put(member1, new Passing(3, 0, 1, 1, 0, 111.0));
        map11.put(member2, new Passing(2, 1, 3, 1, 0, 112.0));
        match1.getResults().put(stage11, map11);

        Map<Member, Passing> map12 = new HashMap<>();
        map12.put(member1, new Passing(1, 2, 0, 0, 2, 121.0));
        map12.put(member2, new Passing(0, 4, 1, 2, 0, 122.0));
        match1.getResults().put(stage12, map12);

        //////////////////

        Stage stage21 = new Stage(1, 3);
        Stage stage22 = new Stage(2, 3);

        Match match2 = new Match("match 2", LocalDate.now());
        match2.getStages().add(stage21);
        match2.getStages().add(stage22);
        match2.getCompetitors().add(member2);
        match2.getCompetitors().add(member3);

        Map<Member, Passing> map21 = new HashMap<>();
        map21.put(member2, new Passing(3, 0, 1, 1, 0, 211.0));
        map21.put(member3, new Passing(4, 1, 1, 0, 2, 212.0));
        match2.getResults().put(stage21, map21);

        Map<Member, Passing> map22 = new HashMap<>();
        map22.put(member2, new Passing(1, 2, 0, 0, 2, 221.0));
        map22.put(member3, new Passing(3, 0, 2, 1, 0, 222.0));
        match2.getResults().put(stage22, map22);

        //////////////////

        return Arrays.asList(match1, match2);
    }
}