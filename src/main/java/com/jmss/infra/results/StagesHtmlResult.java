package com.jmss.infra.results;

import com.jmss.domain.Match;

public final class StagesHtmlResult extends AbstractHtmlResult {

    public StagesHtmlResult(Match match) {
        super(match);
    }

    // TODO add IOException to method signature
    @Override
    public String toHtml() {
        return "";
    }

    @Override
    public String getInitialFileName() {
        return "stages";
    }
}
