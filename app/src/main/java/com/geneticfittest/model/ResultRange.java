package com.geneticfittest.model;

import androidx.annotation.NonNull;

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

    public boolean contains(int score) {
        return score >= min && score <= max;
    }

    @NonNull
    @Override
    public String toString() {
        return getText();
    }
}
