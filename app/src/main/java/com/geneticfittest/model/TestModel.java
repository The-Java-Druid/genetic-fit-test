package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;

public class TestModel {
    private String title;
    private List<Section> sections;
    private Results results;

    public TestModel(String title, List<Section> sections, Results results) {
        this.title = title;
        this.sections = sections;
        this.results = results;
    }

    public TestModel() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public int calculateTotalScore() {
        return sections.stream()
            .mapToInt(Section::calculateTotalScore)
            .sum();
    }

    public String getResultText(){
        return results.getResultText(calculateTotalScore());
    }

    public Section getSection(int index) {
        return sections.get(index);
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }
}
