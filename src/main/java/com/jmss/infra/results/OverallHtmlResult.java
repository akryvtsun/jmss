package com.jmss.infra.results;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.jmss.domain.Match;
import com.jmss.domain.Member;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// TODO all logging
public final class OverallHtmlResult extends AbstractHtmlResult {

    private static final Comparator<Map.Entry<Member, Double>> BACKWARD_COMPARATOR =
            (i1, i2) ->
                    i1.getValue() > i2.getValue()
                        ? 1
                        : i1.getValue() < i2.getValue() ? -1 : 0;

    public OverallHtmlResult(Match match) {
        super(match);
    }

    // TODO add IOException to method signature
    @Override
    public String toHtml() {
        Map<Member, Double> overall = getMatch().overall();

        final AtomicInteger number = new AtomicInteger(0);
        List<OverallRecord> items = overall.entrySet()
                .stream()
                    .sorted(BACKWARD_COMPARATOR)
                    .map(e -> new OverallRecord(number.incrementAndGet(), e.getValue(), e.getKey()))
                    .collect(Collectors.toList());

        // create results HTML string
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("match", getMatch());
        scopes.put("today", LocalDateTime.now());
        scopes.put("items", items);

        MustacheFactory mf = new DefaultMustacheFactory();
        // TODO move to some common constants area???
        Mustache mustache = mf.compile("reports/overall.html");

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
        return "overall";
    }
}
