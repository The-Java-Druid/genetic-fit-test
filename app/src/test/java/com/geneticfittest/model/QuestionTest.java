package com.geneticfittest.model;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.List;

public class QuestionTest {

    public static final Answer BELOW_170 = new Answer("Less than 1.70m", 0);
    public static final Answer ABOVE_170 = new Answer("1.70m to 1.75m", 3);
    public static final Answer ABOVE_175 = new Answer("1.75m to 1.80m", 5);
    public static final Answer ABOVE_180 = new Answer("1.80m to 1.85m", 8);
    public static final Answer ABOVE_185 = new Answer("More than 1.85m", 12);
    public static final List<Answer> ANSWERS = List.of(
        BELOW_170,
        ABOVE_170,
        ABOVE_175,
        ABOVE_180,
        ABOVE_185
    );

    @Test
    public void setSelectedAnswer() {
        final Question question = buildQuestion();
        question.setSelectedAnswer(0);
        assertEquals(BELOW_170, question.getSelectedAnswer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSelectedAnswerNegativeIndex() {
        final Question question = buildQuestion();
        question.setSelectedAnswer(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSelectedAnswerOverflowIndex() {
        final Question question = buildQuestion();
        question.setSelectedAnswer(ANSWERS.size());
    }

    @Test
    public void getScoreCorrectAnswer() {
        final Question question = buildQuestion();
        question.setSelectedAnswer(1);
        assertEquals(3, question.getScore());
    }

    @Test
    public void getScoreNoAnswer() {
        final Question question = buildQuestion();
        assertEquals(0, question.getScore());
    }

    @NonNull
    private static Question buildQuestion() {
        return new Question("How tall are you?", ANSWERS);
    }

}