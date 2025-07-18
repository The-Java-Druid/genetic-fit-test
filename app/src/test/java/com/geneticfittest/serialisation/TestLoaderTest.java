package com.geneticfittest.serialisation;

import static org.junit.Assert.*;

import com.geneticfittest.model.TestModel;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class TestLoaderTest {

    @Test
    public void testLoader() throws IOException {
        //In actual Android code, use Context: context.getAssets().open("test.yml")
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("test.yml")) {
            TestModel test = new TestLoader().load(is);

            assertEquals("Genetic Test for Bodybuilding Potential", test.title());
            assertEquals(6, test.sections().size());
            assertEquals(3, test.results().ranges().size());
        }
    }
}