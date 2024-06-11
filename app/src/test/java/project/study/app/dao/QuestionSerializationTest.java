package project.study.app.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import project.study.app.model.domain.FreeTextQuestion;
import project.study.app.model.domain.MultipleChoiceQuestion;
import project.study.app.model.domain.Question;

public class QuestionSerializationTest {

    @Before
    public void set_up() {

    }

    @Test
    public void from_question_list_to_json_converter() {

        List<Question<?>> questions = new ArrayList<>();

        FreeTextQuestion ftq1 = new FreeTextQuestion("What's the capital of Italy", "Rome");
        MultipleChoiceQuestion mcq1 = new MultipleChoiceQuestion(
                "A nice question",
                new MultipleChoiceQuestion.Answer[]{
                        new MultipleChoiceQuestion.Answer("Answer 1", false),
                        new MultipleChoiceQuestion.Answer("Answer 2", true),
                }
        );

        questions.add(ftq1);
        questions.add(mcq1);

        String jsonString = QuestionListConverter.fromList(questions);
        String expectedJsonString = "[\n" +
                "  {\n" +
                "    \"type\": \"FreeText\",\n" +
                "    \"question\": \"What's the capital of Italy\",\n" +
                "    \"answer\": \"Rome\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"type\": \"MultipleChoice\",\n" +
                "    \"question\": \"A nice question\",\n" +
                "    \"answers\": [\n" +
                "      {\"text\": \"Answer 1\", \"correct\": false},\n" +
                "      {\"text\": \"Answer 2\", \"correct\": true}\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        assertNotNull(jsonString);
        assertEquals(jsonString, expectedJsonString);
    }
}
