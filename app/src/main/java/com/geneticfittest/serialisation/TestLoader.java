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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestLoader {

    private final Yaml yaml = new Yaml(new TestConstructor(new LoaderOptions()));

    public TestModel load(InputStream inputStream) {
        return yaml.loadAs(inputStream, TestModel.class);
    }

    private class TestConstructor extends Constructor {

        private static final Tag SECTION_TAG = new Tag(Section.class);
        private static final Tag QUESTION_TAG = new Tag(Question.class);
        private static final Tag ANSWER_TAG = new Tag(Answer.class);
        private static final Tag RESULTS_TAG = new Tag(Results.class);
        private static final Tag RESULT_RANGE_TAG = new Tag(ResultRange.class);

        public TestConstructor(LoaderOptions loadingConfig) {
            super(TestModel.class, loadingConfig);
            this.yamlClassConstructors.put(NodeId.mapping, new TestModelConstruct());
            this.yamlConstructors.put(SECTION_TAG, new SectionConstruct());
            this.yamlConstructors.put(QUESTION_TAG, new QuestionConstruct());
            this.yamlConstructors.put(ANSWER_TAG, new AnswerConstruct());
            this.yamlConstructors.put(RESULTS_TAG, new ResultsConstruct());
            this.yamlConstructors.put(RESULT_RANGE_TAG, new ResultRangeConstruct());
            Stream.of(TestModel.class, Section.class, Question.class, Answer.class, Results.class, ResultRange.class)
                .map(TypeDescription::new)
                .forEach(this::addTypeDescription);
        }
        private class AbstractTestModelConstruct extends Constructor.ConstructMapping {

            protected String getScalarValue(Node valueNode) {
                return ((ScalarNode)valueNode).getValue();
            }

            protected List<Node> getSequenceValue(Node valueNode) {
                return ((SequenceNode) valueNode).getValue();
            }

            protected List<NodeTuple> getMappingValue(Node node) {
                return ((MappingNode) node).getValue();
            }

            protected <T> List<T> getChildren(NodeTuple tuple, Class<T> type, Tag tag) {
                return getChildren(tuple.getValueNode(), type, tag);
            }

            protected <T> List<T> getChildren(Node node, Class<T> type, Tag tag) {
                return getSequenceValue(node).stream()
                    .map(n -> buildObject(n, type, tag))
                    .collect(Collectors.toList());
            }

            protected <T> T buildObject(Node node, Class<T> type, Tag tag) {
                node.setType(type);
                node.setTag(tag);
                node.setTwoStepsConstruction(false);
                node.setUseClassConstructor(false);
                return (T) constructObject(node);
            }

        }

        private class TestModelConstruct extends AbstractTestModelConstruct {

            @Override
            public Object construct(Node node) {
                node.setTwoStepsConstruction(false);
                String title = null;
                List<Section> sections = null;
                Results results = null;
                for (NodeTuple tuple : getMappingValue(node)) {
                    switch (getScalarValue(tuple.getKeyNode())) {
                        case "title":
                            title = getScalarValue(tuple.getValueNode());
                            break;
                        case "sections":
                            sections = getChildren(tuple, Section.class, SECTION_TAG);
                            break;
                        case "results":
                            results = buildObject(tuple.getValueNode(), Results.class, RESULTS_TAG);
                            break;
                    }
                }
                return new TestModel(title, sections, results);
            }

        }

        private class SectionConstruct extends AbstractTestModelConstruct {

            @Override
            public Object construct(Node node) {
                String name = null;
                List<Question> questions = null;
                for (NodeTuple tuple : getMappingValue(node)) {
                    switch (getScalarValue(tuple.getKeyNode())) {
                        case "name":
                            name = getScalarValue(tuple.getValueNode());
                            break;
                        case "questions":
                            questions = getChildren(tuple, Question.class, QUESTION_TAG);
                            break;
                    }
                }
                return new Section(name, questions);
            }
        }

        private class QuestionConstruct extends AbstractTestModelConstruct {

            @Override
            public Object construct(Node node) {
                String text = null;
                List<Answer> answers = null;
                for (NodeTuple tuple : getMappingValue(node)) {
                    final ScalarNode keyNode = (ScalarNode) tuple.getKeyNode();
                    switch (keyNode.getValue()) {
                        case "text":
                            text = getScalarValue(tuple.getValueNode());
                            break;
                        case "answers":
                            answers = getChildren(tuple, Answer.class, ANSWER_TAG);
                            break;
                    }
                }
                return new Question(text, answers);
            }
        }

        private class AnswerConstruct extends AbstractTestModelConstruct {

            @Override
            public Object construct(Node node) {
                String text = null;
                int score = 0;
                for (NodeTuple tuple : getMappingValue(node)) {
                    switch (getScalarValue(tuple.getKeyNode())) {
                        case "text":
                            text = getScalarValue(tuple.getValueNode());
                            break;
                        case "score":
                            score = Integer.parseInt(getScalarValue(tuple.getValueNode()));
                            break;
                    }
                }
                return new Answer(text, score);
            }
        }

        private class ResultsConstruct extends AbstractTestModelConstruct {

            @Override
            public Object construct(Node node) {
                return new Results(getChildren(node, ResultRange.class, RESULT_RANGE_TAG));
            }

        }

        private class ResultRangeConstruct extends AbstractTestModelConstruct {
            @Override
            public Object construct(Node node) {
                int min = 0;
                int max = 0;
                String text = null;
                for (NodeTuple tuple : getMappingValue(node)) {
                    switch (getScalarValue(tuple.getKeyNode())) {
                        case "min":
                            min = Integer.parseInt(getScalarValue(tuple.getValueNode()));
                            break;
                        case "max":
                            max = Integer.parseInt(getScalarValue(tuple.getValueNode()));
                            break;
                        case "text":
                            text = getScalarValue(tuple.getValueNode());
                            break;
                    }
                }
                return new ResultRange(min, max, text);
            }
        }
    }
}
