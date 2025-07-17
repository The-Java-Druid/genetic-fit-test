package com.geneticfittest.serialisation;

import com.geneticfittest.model.TestModel;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.yamlrecords.RecordConstructor;

import java.io.InputStream;

public class TestLoader {

    private final Yaml yaml = new Yaml(new RecordConstructor(TestModel.class));

    public TestModel load(InputStream inputStream) {
        return yaml.loadAs(inputStream, TestModel.class);
    }

}
