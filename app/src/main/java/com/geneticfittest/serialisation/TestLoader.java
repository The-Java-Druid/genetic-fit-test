package com.geneticfittest.serialisation;

import com.geneticfittest.model.Answer;
import com.geneticfittest.model.Question;
import com.geneticfittest.model.ResultRange;
import com.geneticfittest.model.Results;
import com.geneticfittest.model.Section;
import com.geneticfittest.model.TestModel;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestLoader {

    private static final Tag SECTION_TAG = new Tag(Section.class);
    private static final Tag RESULTS_TAG = new Tag(Results.class);
    private static final Tag QUESTION_TAG = new Tag(Question.class);
    private static final Tag ANSWER_TAG = new Tag(Answer.class);
    private static final Tag RESULT_RANGE_TAG = new Tag(ResultRange.class);
    private final Yaml yaml = new Yaml(new TestConstructor(new LoaderOptions()));

    public TestModel load(InputStream inputStream) {
        return yaml.loadAs(inputStream, TestModel.class);
    }

    private class TestConstructor extends Constructor {

        public TestConstructor(LoaderOptions loadingConfig) {
            super(TestModel.class, loadingConfig);
            this.yamlClassConstructors.put(NodeId.mapping, new TestModelConstructor());
            this.yamlConstructors.put(SECTION_TAG, new SectionConstructor());
            this.yamlConstructors.put(QUESTION_TAG, new QuestionConstructor());
            this.yamlConstructors.put(ANSWER_TAG, new AnswerConstructor());
            this.yamlConstructors.put(RESULT_RANGE_TAG, new ResultRangeConstructor());
            this.yamlConstructors.put(RESULTS_TAG, new ResultsConstructor());

            addTypeDescription(new TypeDescription(TestModel.class));
            addTypeDescription(new TypeDescription(Section.class));
            addTypeDescription(new TypeDescription(Question.class));
            addTypeDescription(new TypeDescription(Answer.class));
            addTypeDescription(new TypeDescription(Results.class));
            addTypeDescription(new TypeDescription(ResultRange.class));
        }

        private class TestModelConstructor extends ConstructMapping {

            @Override
            public Object construct(Node node) {
                node.setTwoStepsConstruction(false);
                final MappingNode mappingNode = (MappingNode) node;
                String title = null;
                final List<Section> sections = new ArrayList<>();
                Results results = null;
                for (NodeTuple tuple : mappingNode.getValue()) {
                    final ScalarNode keyNode = (ScalarNode) tuple.getKeyNode();
                    final String value = keyNode.getValue();
                    final Node valueNode = tuple.getValueNode();
                    switch (value) {
                        case "title":
                            final ScalarNode titleNode = (ScalarNode) valueNode;
                            title = titleNode.getValue(); // Convert value node to String
                            break;
                        case "sections":
                            final SequenceNode sectionsNode = (SequenceNode) valueNode;
                            for (Node sectionNode : sectionsNode.getValue()) {
                                sectionNode.setType(Section.class);
                                sectionNode.setTag(SECTION_TAG);
                                sectionNode.setTwoStepsConstruction(false);
                                sectionNode.setUseClassConstructor(false);
                                sections.add((Section) constructObject(sectionNode)); // Recursively construct Section objects
                            }
                            break;
                        case "results":
                            valueNode.setType(Results.class);
                            valueNode.setTag(RESULTS_TAG);
                            valueNode.setTwoStepsConstruction(false);
                            valueNode.setUseClassConstructor(false);
                            results = (Results) constructObject(valueNode); // Recursively construct Results object
                            break;
                    }
                }
                return new TestModel(title, sections, results);
            }

        }

        private class SectionConstructor extends ConstructMapping {

            @Override
            public Object construct(Node node) {
                final MappingNode mappingNode = (MappingNode) node;
                String name = null;
                final List<Question> questions = new ArrayList<>();
                for (NodeTuple tuple : mappingNode.getValue()) {
                    final ScalarNode keyNode = (ScalarNode) tuple.getKeyNode();
                    final String value = keyNode.getValue();
                    final Node valueNode = tuple.getValueNode();
                    switch (value) {
                        case "name":
                            final ScalarNode nameNode = (ScalarNode) valueNode;
                            name = nameNode.getValue(); // Convert value node to String
                            break;
                        case "questions":
                            final SequenceNode questionsNode = (SequenceNode) valueNode;
                            for (Node questionNode : questionsNode.getValue()) {
                                questionNode.setType(Question.class);
                                questionNode.setTag(QUESTION_TAG);
                                questionNode.setTwoStepsConstruction(false);
                                questionNode.setUseClassConstructor(false);
                                questions.add((Question) constructObject(questionNode)); // Recursively construct Question objects
                            }
                            break;
                    }
                }
                return new Section(name, questions);
            }
        }

        private class QuestionConstructor extends ConstructMapping {
            @Override
            public Object construct(Node node) {
                final MappingNode mappingNode = (MappingNode) node;
                String text = null;
                final List<Answer> answers = new ArrayList<>();
                for (NodeTuple tuple : mappingNode.getValue()) {
                    final ScalarNode keyNode = (ScalarNode) tuple.getKeyNode();
                    final String value = keyNode.getValue();
                    final Node valueNode = tuple.getValueNode();
                    switch (value) {
                        case "text":
                            final ScalarNode textNode = (ScalarNode) valueNode;
                            text = textNode.getValue(); // Convert value node to String
                            break;
                        case "answers":
                            final SequenceNode answersNode = (SequenceNode) valueNode;
                            for (Node answerNode : answersNode.getValue()) {
                                answerNode.setType(Answer.class);
                                answerNode.setTag(ANSWER_TAG);
                                answerNode.setTwoStepsConstruction(false);
                                answerNode.setUseClassConstructor(false);
                                answers.add((Answer) constructObject(answerNode)); // Recursively construct Answer objects
                            }
                            break;

                    }
                }
                return new Question(text, answers);
            }
        }

        private class AnswerConstructor extends ConstructMapping {
            @Override
            public Object construct(Node node) {
                final MappingNode mappingNode = (MappingNode) node;
                String text = null;
                int score = 0;
                for (NodeTuple tuple : mappingNode.getValue()) {
                    final ScalarNode keyNode = (ScalarNode) tuple.getKeyNode();
                    final String value = keyNode.getValue();
                    final Node valueNode = tuple.getValueNode();
                    switch (value) {
                        case "text":
                            final ScalarNode textNode = (ScalarNode) valueNode;
                            text = textNode.getValue(); // Convert value node to String
                            break;
                        case "score":
                            final ScalarNode scoreNode = (ScalarNode) valueNode;
                            score = Integer.parseInt(scoreNode.getValue()); // Convert value node to String
                            break;
                    }
                }
                return new Answer(text, score);
            }
        }

        private class ResultRangeConstructor extends ConstructMapping {
            @Override
            public Object construct(Node node) {
                final MappingNode mappingNode = (MappingNode) node;
                int min = 0;
                int max = 0;
                String text = null;
                for (NodeTuple tuple : mappingNode.getValue()) {
                    final ScalarNode keyNode = (ScalarNode) tuple.getKeyNode();
                    final String value = keyNode.getValue();
                    final Node valueNode = tuple.getValueNode();
                    switch (value) {
                        case "min":
                            final ScalarNode minNode = (ScalarNode) valueNode;
                            min = Integer.parseInt(minNode.getValue()); // Convert value node to String
                            break;
                        case "max":
                            final ScalarNode maxNode = (ScalarNode) valueNode;
                            max = Integer.parseInt(maxNode.getValue()); // Convert value node to String
                            break;
                        case "text":
                            final ScalarNode textNode = (ScalarNode) valueNode;
                            text = textNode.getValue(); // Convert value node to String
                            break;
                    }
                }
                return new ResultRange(min, max, text);
            }
        }

        private class ResultsConstructor extends ConstructMapping {

            @Override
            public Object construct(Node node) {
                final SequenceNode sequenceNode = (SequenceNode) node;
                final List<ResultRange> ranges = new ArrayList<>();
                for (Node sequenceValue : sequenceNode.getValue()) {
                    final MappingNode mappingNode = (MappingNode) sequenceValue;
                    mappingNode.setType(ResultRange.class);
                    mappingNode.setTag(RESULT_RANGE_TAG);
                    mappingNode.setTwoStepsConstruction(false);
                    mappingNode.setUseClassConstructor(false);
                    ranges.add((ResultRange) constructObject(mappingNode)); // Recursively construct ResultRange objects
                }
                return new Results(ranges);
            }
        }
    }
}
