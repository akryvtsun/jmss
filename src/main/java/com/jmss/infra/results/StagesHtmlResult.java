package com.jmss.infra.results;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.jmss.domain.Match;
import com.jmss.domain.Member;
import com.jmss.domain.Stage;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class StagesHtmlResult extends AbstractHtmlResult {

    private static final Comparator<Map.Entry<Member, Double>> BACKWARD_COMPARATOR =
            (i1, i2) ->
                    i1.getValue() > i2.getValue()
                            ? 1
                            : i1.getValue() < i2.getValue() ? -1 : 0;

    public StagesHtmlResult(Match match) {
        super(match);
    }

    // TODO add IOException to method signature
    @Override
    public String toHtml() {

        Map<Stage, List<OverallRecord>> stages = new HashMap<>();
        for (Stage stage: getMatch().getStages()) {
            Map<Member, Double> stageResult = getMatch().result(stage);

            final AtomicInteger number = new AtomicInteger(0);
            List<OverallRecord> items = stageResult.entrySet()
                    .stream()
                    .sorted(BACKWARD_COMPARATOR)
                    .map(e -> new OverallRecord(number.incrementAndGet(), e.getValue(), e.getKey()))
                    .collect(Collectors.toList());

            stages.put(stage, items);
        }

        // create results HTML string
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("match", getMatch());
        scopes.put("today", LocalDateTime.now());
        scopes.put("stages", stages.entrySet());

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("reports/stages.html");

        StringWriter sw = new StringWriter();

        try {
            mustache.execute(sw, scopes).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }

    @Override
    public String getInitialFileName() {
        return "stages";
    }
}
