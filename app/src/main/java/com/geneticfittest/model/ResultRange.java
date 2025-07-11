package com.geneticfittest.model;

public class ResultRange {
    final int min;
    final int max;
    final String text;

    public ResultRange(int min, int max, String text) {
        this.min = min;
        this.max = max;
        this.text = text;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return getText();
    }
}
