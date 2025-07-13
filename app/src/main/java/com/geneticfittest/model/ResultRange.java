package com.geneticfittest.model;

import androidx.annotation.NonNull;

public record ResultRange(int min, int max, String text) {

    public boolean contains(int score) {
        return score >= min && score <= max;
    }

    @NonNull
    @Override
    public String toString() {
        return text();
    }
}
