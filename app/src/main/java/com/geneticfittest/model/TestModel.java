package com.geneticfittest.model;

import java.util.List;

public class TestModel {
    private final String title;
    private final List<Section> sections;
    private final int[] selectedAnswers; // index of selected answer per section

    public TestModel(String title, List<Section> sections, int[] selectedAnswers) {
        this.title = title;
        this.sections = sections;
        this.selectedAnswers = selectedAnswers;
    }

    public String getTitle() {
        return title;
    }

    public List<Section> getSections() {
        return sections;
    }

    public int[] getSelectedAnswers() {
        return selectedAnswers;
    }

    public int getSelectedAnswer(int sectionIndex) {
        return selectedAnswers[sectionIndex];
    }

    public int calculateTotalScore() {
        return sections.stream()
                .mapToInt(Section::calculateTotalScore)
                .sum();
    }
}
