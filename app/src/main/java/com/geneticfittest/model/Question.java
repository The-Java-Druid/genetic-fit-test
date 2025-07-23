package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Optional;

public record Question(String text, List<Answer> answers, Holder<Integer> selectedAnswer) {
    public Question(String text, List<Answer> answers) {
        this(text, answers, new Holder<>());
    }

    public void setSelectedAnswerIndex(int index) {
        if (index >= 0 && index < answers.size()) {
            selectedAnswer.set(index);
        } else {
            throw new IllegalArgumentException("Invalid answer index");
        }
    }

    public int selectedAnswerIndex() {
        return hasSelectedAnswer()? selectedAnswer.get(): -1;
    }

    public boolean hasSelectedAnswer() {
        return selectedAnswer.hasValue();
    }

    public Optional<Answer> getSelectedAnswer() {
        return hasSelectedAnswer()?
            Optional.of(answers.get(selectedAnswer.get())): Optional.empty();
    }
    public int score() {
        return getSelectedAnswer()
            .map(Answer::score)
            .orElse(0);
    }

    @NonNull
    @Override
    public String toString() {
        return text();
    }
}
