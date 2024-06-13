package project.study.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import project.study.app.model.dao.QuestionListConverter;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;

public class JsonSerializationTest {

    private List<Question> questions;

    @Before
    public void set_up() {
        this.questions = new ArrayList<>();
    }

    @Test
    public void from_question_list_to_json_converter() {

        Question question1 = new Question(
                "What is my first name?",
                new FreeTextAnswer("Davide")
        );

        // add those questions to the list of questions:
        this.questions.add( question1 );

        List<String> possibleAnswers = new ArrayList<>();
        possibleAnswers.add("Nessuno");
        possibleAnswers.add("Non so");
        Question question2 = new Question(
                "What is my second name?",
                new MultipleChoiceTextAnswer(possibleAnswers, "Nessuno")
        );

        // add those questions to the list of questions:
        this.questions.add( question2 );

        // serialize the question list --> JSON:
        String jsonString = QuestionListConverter.fromList(
                this.questions
        );

        System.out.println(jsonString);

        // the expected JSON String:
        String expectedJsonString = "[{\"questionText\":\"What is my first name?\",\"answerType\":\"FreeTextAnswer\",\"correctAnswer\":\"Davide\"},{\"questionText\":\"What is my second name?\",\"answerType\":\"MultipleChoiceAnswer\",\"possibleAnswers\":[{\"answer_0\":\"Nessuno\"},{\"answer_1\":\"Non so\"}],\"correctAnswer\":\"Nessuno\"}]";


        // jsonString should not be null:
        assertNotNull(jsonString);
        // expectedJsonString and jsonString should be equal:
        assertEquals(expectedJsonString, jsonString);
    }

    @Test
    public void from_json_string_to_question_list() {

    }
}
