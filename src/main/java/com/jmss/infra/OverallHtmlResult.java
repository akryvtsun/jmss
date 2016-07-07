package com.jmss.infra;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.jmss.domain.Match;
import com.jmss.domain.Member;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public final class OverallHtmlResult {

    private final Match match;

    public OverallHtmlResult(Match match) {
        this.match = match;
    }

    // TODO add IOException to method signature
    public String toHtml() {
        Map<Member, Double> overall = match.overall();

        // create results HTML string
        Map<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("match", match);
        scopes.put("overall", overall.entrySet());

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("overall.html");

        StringWriter sw = new StringWriter();

        try {
            mustache.execute(sw, scopes).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }
}
