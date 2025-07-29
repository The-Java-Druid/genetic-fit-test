package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Optional;

public class Question {
    private String text;
    private List<Answer> answers;
    private Optional<Integer> selectedAnswer;

    public Question(String text, List<Answer> answers, Optional<Integer> selectedAnswer) {
        this.text = text;
        this.answers = answers;
        this.selectedAnswer = selectedAnswer;
    }

    public Question(String text, List<Answer> answers) {
        this(text, answers, Optional.empty());
    }

    public Question() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setSelectedAnswer(Optional<Integer> selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public void setSelectedAnswerIndex(int index) {
        if (index >= 0 && index < answers.size()) {
            selectedAnswer = Optional.of(index);
        } else {
            throw new IllegalArgumentException("Invalid answer index");
        }
    }

    public int getSelectedAnswerIndex() {
        return selectedAnswer.orElse(-1);
    }

    public boolean hasSelectedAnswer() {
        return selectedAnswer.isPresent() ;
    }

    public Optional<Answer> getSelectedAnswer() {
        return selectedAnswer.map(answers::get);
    }
    public int score() {
        return getSelectedAnswer()
            .map(Answer::getScore)
            .orElse(0);
    }

    @NonNull
    @Override
    public String toString() {
        return getText();
    }
}
