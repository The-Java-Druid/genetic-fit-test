package com.geneticfittest.serialisation;

import static org.junit.Assert.*;

import com.geneticfittest.model.TestModel;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class TestLoaderTest {

    @Test
    public void testLoader() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("test.yml")) {
            TestModel test = new TestLoader().load(is);

            assertEquals("Genetic Test for Bodybuilding Potential", test.getTitle());
            assertEquals(6, test.getSections().size());
            assertEquals(3, test.getResults().getRanges().size());
        }
    }
}