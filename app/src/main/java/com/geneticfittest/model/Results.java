package com.geneticfittest.model;

import java.util.List;

public class Results {
    public static final String UNKNOWN_RESULT_TEXT = "Unknown";

    private List<ResultRange> ranges;

    public Results(List<ResultRange> ranges) {
        this.ranges = ranges;
    }

    public Results() {}

    public List<ResultRange> getRanges() {
        return ranges;
    }

    public void setRanges(List<ResultRange> ranges) {
        this.ranges = ranges;
    }

    public String getResultText(int score) {
        return ranges.stream()
            .filter(range -> range.contains(score))
            .map(ResultRange::getText)
            .findFirst().orElse(UNKNOWN_RESULT_TEXT);
    }
}