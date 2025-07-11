package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;

public class TestModel {
    private final String title;
    private final List<Section> sections;
    private final Results results;

    public TestModel(String title, List<Section> sections, Results results) {
        this.title = title;
        this.sections = sections;
        this.results = results;
    }

    public String getTitle() {
        return title;
    }

    public List<Section> getSections() {
        return sections;
    }

    public int calculateTotalScore() {
        return sections.stream()
                .mapToInt(Section::calculateTotalScore)
                .sum();
    }

    public String getResultText(){
        return results.getResultText(calculateTotalScore());
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }
}
