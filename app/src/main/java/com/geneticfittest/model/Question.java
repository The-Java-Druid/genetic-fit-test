package com.geneticfittest.model;

import java.util.List;

public class Question {
    private final String text;
    private final List<Answer> answers;
    private Answer selectedAnswer;

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Answer getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(Answer selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public void setSelectedAnswer(int index) {
        if (index >= 0 && index < answers.size()) {
            selectedAnswer = answers.get(index);
        } else {
            throw new IllegalArgumentException("Invalid answer index");
        }
    }

    public int getScore() {
        if (selectedAnswer != null) {
            return selectedAnswer.getScore();
        }
        return 0;
    }

    @Override
    public String toString() {
        return getText();
    }
}
