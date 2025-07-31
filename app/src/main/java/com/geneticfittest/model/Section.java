package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Section {
    private String name;
    private List<Question> questions;

    public Section(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public Section() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int calculateTotalScore() {
        return questions.stream()
                .mapToInt(Question::score)
                .sum();
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
