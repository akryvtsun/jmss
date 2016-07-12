package com.jmss.infra.results;

import com.jmss.domain.Match;
import com.jmss.domain.Member;
import com.jmss.domain.Stage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StagesHtmlResult extends AbstractHtmlResult {

    public StagesHtmlResult(Match match) {
        super(match, "reports/stages.html");
    }

    // TODO add IOException to method signature
    @Override
    public String toHtml() {

        Map<Stage, List<CompetitorRecord>> stages = new HashMap<>();
        for (Stage stage: getMatch().getStages()) {
            Map<Member, Double> results = getMatch().result(stage);
            stages.put(stage, createRecords(results));
        }

        // create results HTML string
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("match", getMatch());
        scopes.put("today", LocalDateTime.now());
        scopes.put("stages", stages.entrySet());

        return createHtmlResult(scopes);
    }

    @Override
    public String getInitialFileName() {
        return "stages";
    }
}
