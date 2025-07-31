package com.geneticfittest.model;

import androidx.annotation.NonNull;

public class ResultRange {
    private int min;
    private int max;
    private String text;

    public ResultRange(int min, int max, String text) {
        this.min = min;
        this.max = max;
        this.text = text;
    }

    public ResultRange() {
    }

    public String getText() {
        return text;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setText(String text) {
        this.text = text;
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
