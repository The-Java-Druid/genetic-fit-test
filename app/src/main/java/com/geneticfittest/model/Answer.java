package com.geneticfittest.model;

import androidx.annotation.NonNull;

public class Answer {
    private String text;
    private int score;

    public Answer(String text, int score) {
        this.text = text;
        this.score = score;
    }

    public Answer() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @NonNull
    @Override
    public String toString() {
        return getText();
    }
}
