package com.geneticfittest.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.List;

public class TestModelTest {

    public static final List<Section> SECTIONS = List.of(
        new Section("General shape", List.of(
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
        )),
        new Section("Abs", List.of(
            new Question("How symmetrical are your abs?", List.of(
                new Answer("Completely symmetrical", 4),
                new Answer("Asymmetrical", 0)
            )),
            new Question("How squared are your abs", List.of(
                new Answer("Squared/rectangular", 2),
                new Answer("Irregular", 0)
            )),
            new Question("How wide is the gap between your abs?", List.of(
                new Answer("Very narrow gap", 4),
                new Answer("Wide gap", 0)
            )),
            new Question("How many pack?", List.of(
                new Answer("4-pack", 1),
                new Answer("6-pack+", 2)
            ))
        )),
        new Section("Pecs", List.of(
            new Question("How symmetrical are your pecs?", List.of(
                new Answer("Completely symmetrical", 4),
                new Answer("Asymmetrical", 0)
            )),
            new Question("How squared are your pecs", List.of(
                new Answer("Squared/rectangular", 4),
                new Answer("Rounded/Irregular", 0)
            )),
            new Question("How wide is the gap between your pecs?", List.of(
                new Answer("Very narrow gap", 4),
                new Answer("Very narrow gap at the top, but widens at the bottom", 2),
                new Answer("Wide gap", 0)
            ))
        )),
        new Section("Calves", List.of(
            new Question("How long are your tendons?", List.of(
                new Answer("Short", 6),
                new Answer("Medium", 3),
                new Answer("Long", 0)
            ))
        )),
        new Section("Biceps", List.of(
            new Question("How long are your tendons?", List.of(
                new Answer("Short", 5),
                new Answer("Long", 0)
            ))
        )),
        new Section("Skin", List.of(
            new Question("Can you see your muscles details through your skin?", List.of(
                new Answer("Yes", 2),
                new Answer("No", 0)
            ))
        ))
    );
    public static final Results RESULTS = new Results(List.of(
        new ResultRange(0, 35, "You may face genetic challenges..."),
        new ResultRange(36, 50, "You've got a decent potential..."),
        new ResultRange(51, 256, "You've got a privileged physique...")
    ));

    @Test
    public void calculateTotalScore() {
        final TestModel cr7 = new TestModel("Genetic Test for Bodybuilding Potential", SECTIONS, RESULTS);
        final Section generalShape = cr7.getSections().get(0);
        final List<Question> generalShapeQuestions = generalShape.getQuestions();
        generalShapeQuestions.get(0).setSelectedAnswer(4);
        generalShapeQuestions.get(1).setSelectedAnswer(2);
        generalShapeQuestions.get(2).setSelectedAnswer(2);

        final Section abs = cr7.getSections().get(1);
        final List<Question> absQuestions = abs.getQuestions();
        absQuestions.get(0).setSelectedAnswer(1);
        absQuestions.get(1).setSelectedAnswer(1);
        absQuestions.get(2).setSelectedAnswer(0);
        absQuestions.get(3).setSelectedAnswer(1);

        final Section pecs = cr7.getSections().get(2);
        final List<Question> pecsQuestions = pecs.getQuestions();
        pecsQuestions.get(0).setSelectedAnswer(0);
        pecsQuestions.get(1).setSelectedAnswer(0);
        pecsQuestions.get(2).setSelectedAnswer(1);

        final Section calves = cr7.getSections().get(3);
        calves.getQuestions()
            .get(0).setSelectedAnswer(1);

        final Section biceps = cr7.getSections().get(4);
        biceps.getQuestions()
            .get(0).setSelectedAnswer(0);

        final Section skin = cr7.getSections().get(5);
        skin.getQuestions()
            .get(0).setSelectedAnswer(1);

        assertEquals(54, cr7.calculateTotalScore());
        assertEquals("You've got a privileged physique...", cr7.getResultText());
    }
}