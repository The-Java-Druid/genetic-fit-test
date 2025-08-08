package com.geneticfittest.model;

import androidx.annotation.NonNull;

import java.util.List;

public record TestModel(String title, List<Section> sections, Results results) {

    public int totalScore() {
        return sections.stream()
            .mapToInt(Section::totalScore)
            .sum();
    }

    public void resetAnswers() {
        sections.stream()
            .map(Section::questions)
            .flatMap(List::stream)
            .forEach(Question::clearAnswer);
    }

    public String resultText(){
        return results.resultText(totalScore());
    }

    public Section section(int index) {
        return sections.get(index);
    }

    @NonNull
    @Override
    public String toString() {
        return title();
    }
}
