package com.geneticfittest.model;

import androidx.annotation.NonNull;

public record Answer (String text, int score) {

    @NonNull
    @Override
    public String toString() {
        return text();
    }
}
