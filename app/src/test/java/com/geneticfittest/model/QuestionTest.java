package com.geneticfittest.model;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.List;

public class QuestionTest {

    public static final Answer PARIS = new Answer("Paris", 1);
    public static final Answer LONDON = new Answer("London", 0);
    public static final Answer BERLIN = new Answer("Berlin", 0);
    public static final Answer MADRID = new Answer("Madrid", 0);
    public static final List<Answer> CAPITALS = List.of(
        PARIS,
        LONDON,
        BERLIN,
        MADRID);

    @Test
    public void setSelectedAnswer() {
        final Question question = buildQuestion();
        question.setSelectedAnswer(0);
        assertEquals(PARIS, question.getSelectedAnswer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSelectedAnswerNegativeIndex() {
        final Question question = buildQuestion();
        question.setSelectedAnswer(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSelectedAnswerOverflowIndex() {
        final Question question = buildQuestion();
        question.setSelectedAnswer(CAPITALS.size());
    }

    @Test
    public void getScoreCorrectAnswer() {
        final Question question = buildQuestion();
        question.setSelectedAnswer(0);
        assertEquals(1, question.getScore());
    }

    @NonNull
    private static Question buildQuestion() {
        return new Question("What is the capital of France?", CAPITALS);
    }

}