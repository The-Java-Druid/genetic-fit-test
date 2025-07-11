package com.geneticfittest.model;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.List;

public class ResultsTest {

    public static final ResultRange NOT_BAD = new ResultRange(1, 10, "Not bad");
    public static final ResultRange PRETTY_GOOD = new ResultRange(11, 20, "Pretty good, actually");

    @Test
    public void testGetResultText() {
        assertEquals("Pretty good, actually", getResults().getResultText(15));
    }

    @Test
    public void testGetResultTextUnderScore() {
        assertEquals(Results.UNKNOWN_RESULT_TEXT, getResults().getResultText(0));
    }

    @Test
    public void testGetResultTextOverScore() {
        assertEquals(Results.UNKNOWN_RESULT_TEXT, getResults().getResultText(0));
    }

    @NonNull
    private static Results getResults() {
        return new Results(List.of(NOT_BAD, PRETTY_GOOD));
    }

}

