package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Section {
    private final String name;
    private final List<Question> questions;

    public Section(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int calculateTotalScore() {
        return questions.stream()
                .mapToInt(Question::getScore)
                .sum();
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
