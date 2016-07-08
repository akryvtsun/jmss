package com.jmss.infra.results;

import com.jmss.domain.Match;

abstract class AbstractHtmlResult implements HtmlResult {

    private final Match match;

    public AbstractHtmlResult(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }
}
