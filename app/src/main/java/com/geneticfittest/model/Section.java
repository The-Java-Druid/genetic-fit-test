package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;

public record Section(String name, List<Question> questions) {

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
        return name();
    }
}
