package com.jmss.infra.results;

public interface HtmlResult {
    // TODO refactor with Enum for specifying media and get rid of boolean value???
    String toHtml(boolean toPdf);
    String getInitialFileName();
}
