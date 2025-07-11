package com.geneticfittest.model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

public class ResultsTest {

    @Test
    public void testGetResultText() {
        final Results instance = new Results(List.of(
            new ResultRange(1, 10, "Not bad"),
            new ResultRange(11, 20, "Pretty good, actually")));
        assertEquals("Pretty good, actually", instance.getResultText(15));
    }

    @Test
    public void testGetResultTextUnderScore() {
        final Results instance = new Results(List.of(
            new ResultRange(1, 10, "Not bad"),
            new ResultRange(11, 20, "Pretty good, actually")));
        assertEquals(Results.UNKNOWN_RESULT_TEXT, instance.getResultText(0));
    }

    @Test
    public void testGetResultTextOverScore() {
        final Results instance = new Results(List.of(
            new ResultRange(1, 10, "Not bad"),
            new ResultRange(11, 20, "Pretty good, actually")));
        assertEquals(Results.UNKNOWN_RESULT_TEXT, instance.getResultText(0));
    }
}

