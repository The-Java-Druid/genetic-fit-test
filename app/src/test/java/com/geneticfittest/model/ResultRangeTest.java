package com.geneticfittest.model;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;
import org.junit.Test;

public class ResultRangeTest {

    @Test
    public void contains() {
        final ResultRange instance = getRange();
        assertTrue(instance.contains(15));
    }

    @Test
    public void doesNotContainBelow() {
        final ResultRange instance = getRange();
        assertFalse(instance.contains(5));
    }

    @Test
    public void doesNotContainAbove() {
        final ResultRange instance = getRange();
        assertFalse(instance.contains(25));
    }

    @NonNull
    private static ResultRange getRange() {
        return new ResultRange(10, 20, "Hmmm, you could improve");
    }
}