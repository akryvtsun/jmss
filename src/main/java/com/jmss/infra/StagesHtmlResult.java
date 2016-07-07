package com.jmss.infra;

import com.jmss.domain.Match;

public final class StagesHtmlResult {

    private final Match match;

    public StagesHtmlResult(Match match) {
        this.match = match;
    }

    // TODO add IOException to method signature
    public String toHtml() {
        return "";
    }
}
