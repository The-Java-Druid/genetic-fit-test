package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;

public record TestModel(String title, List<Section> sections, Results results) {

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
        return title();
    }
}
