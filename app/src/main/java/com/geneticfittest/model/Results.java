package com.geneticfittest.model;

import java.util.List;

public class Results {
    public static final String UNKNOWN_RESULT_TEXT = "Unknown";
    final private List<ResultRange> ranges;

    public Results(List<ResultRange> ranges) {
        this.ranges = ranges;
    }

    public List<ResultRange> getRanges() {
        return ranges;
    }

    public String getResultText(int score) {
        return ranges.stream()
            .filter(range -> score >= range.getMin() && score <= range.getMax())
            .map(ResultRange::getText)
            .findFirst().orElse(UNKNOWN_RESULT_TEXT);
    }
}