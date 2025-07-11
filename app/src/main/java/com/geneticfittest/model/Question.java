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

    public int getScore() {
        return selectedAnswer.getScore();
    }
}
