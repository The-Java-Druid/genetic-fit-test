package com.geneticfittest.model;

import java.util.List;

public record Results(List<ResultRange> ranges) {
    public static final String UNKNOWN_RESULT_TEXT = "Unknown";

    public String getResultText(int score) {
        return ranges.stream()
            .filter(range -> range.contains(score))
            .map(ResultRange::text)
            .findFirst().orElse(UNKNOWN_RESULT_TEXT);
    }
}