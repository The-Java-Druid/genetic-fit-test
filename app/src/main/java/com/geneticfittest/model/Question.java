package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;

public record Question(String text, List<Answer> answers, Holder<Answer> selectedAnswer) {
    public Question(String text, List<Answer> answers) {
        this(text, answers, new Holder<>());
    }

    public void setSelectedAnswer(int index) {
        if (index >= 0 && index < answers.size()) {
            selectedAnswer.set(answers.get(index));
        } else {
            throw new IllegalArgumentException("Invalid answer index");
        }
    }

    public int getScore() {
        if (selectedAnswer.hasValue()) {
            return selectedAnswer.get().score();
        }
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return text();
    }
}
