package com.geneticfittest.model;

public class Answer {
    private final String text;
    private final int score;

    public Answer(String text, int score) {
        this.text = text;
        this.score = score;
    }
    public String getText() {
        return text;
    }
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return getText();
    }
}
