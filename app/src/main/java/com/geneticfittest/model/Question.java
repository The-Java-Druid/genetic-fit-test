package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Optional;

public record Question(String text, List<Answer> answers, Holder<Integer> selectedAnswerIndexHolder) {

    public Question(String text, List<Answer> answers) {
        this(text, answers, new Holder<>());
    }

    public void setSelectedAnswerIndex(int index) {
        if (index >= 0 && index < answers.size()) {
            selectedAnswerIndexHolder.set(index);
        } else {
            throw new IllegalArgumentException("Invalid answer index");
        }
    }

    public void clearAnswer() {
        this.selectedAnswerIndexHolder.set(null);
    }

    public int selectedAnswerIndex() {
        return hasSelectedAnswer()? selectedAnswerIndexHolder.get(): (-1);
    }

    public boolean hasSelectedAnswer() {
        return selectedAnswerIndexHolder.hasValue() ;
    }

    public Optional<Answer> getSelectedAnswer() {
        return Optional.ofNullable(hasSelectedAnswer()?
            answers.get(selectedAnswerIndexHolder.get()): null);
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
