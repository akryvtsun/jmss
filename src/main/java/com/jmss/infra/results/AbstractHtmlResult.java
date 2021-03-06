package com.jmss.infra.results;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.jmss.domain.Match;
import com.jmss.domain.Member;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;

abstract class AbstractHtmlResult implements HtmlResult {

    private static final Comparator<Map.Entry<Member, Double>> BACKWARD_COMPARATOR =
            (i1, i2) ->
                    i1.getValue() > i2.getValue()
                            ? 1
                            : i1.getValue() < i2.getValue() ? -1 : 0;

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .toFormatter();

    private final Match match;
    private final String report;

    public AbstractHtmlResult(Match match, String report) {
        this.match = match;
        this.report = report;
    }

    public Match getMatch() {
        return match;
    }

    protected List<CompetitorRecord> createRecords(Map<Member, Double> results) {
        final AtomicInteger number = new AtomicInteger(0);

        List<CompetitorRecord> records = results.entrySet()
                .stream()
                .sorted(BACKWARD_COMPARATOR)
                .map(e -> new CompetitorRecord(number.incrementAndGet(), e.getValue(), e.getKey()))
                .collect(Collectors.toList());

        return records;
    }

    protected String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ISO_DATE) + " at " + now.format(TIME_FORMATTER);
    }

    protected String createHtmlResult(Map<String, Object> scopes) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(report);

        // TODO use here try-resource
        StringWriter sw = new StringWriter();
        try {
            mustache.execute(sw, scopes).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
