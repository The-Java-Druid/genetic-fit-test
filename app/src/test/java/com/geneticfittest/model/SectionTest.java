package com.geneticfittest.model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

public class SectionTest {

    @Test
    public void calculateTotalScore() {
        final Section instance = new Section("General shape", List.of(
            new Question("How tall are you?", List.of(
                new Answer("Less than 1.70m", 0),
                new Answer("1.70m to 1.75m", 3),
                new Answer("1.75m to 1.80m", 5),
                new Answer("1.80m to 1.85m", 8),
                new Answer("More than 1.85m", 12)
            )),
            new Question("How quickly do you lose metabolism and gain muscle??", List.of(
                new Answer("Meh...", 4),
                new Answer("Could do better", 6),
                new Answer("Beast", 8)
            )),
            new Question("How much wider are your shoulders than your hips?", List.of(
                new Answer("Same width", 1),
                new Answer("Around 1.5", 3),
                new Answer("Above 1.75", 10),
                new Answer("Above 2.00", 15)
            ))
        ));
        final List<Question> questions = instance.getQuestions();
        questions.get(0).setSelectedAnswerIndex(4);
        questions.get(1).setSelectedAnswerIndex(2);
        questions.get(2).setSelectedAnswerIndex(2);

        assertEquals(30, instance.calculateTotalScore());
    }
}