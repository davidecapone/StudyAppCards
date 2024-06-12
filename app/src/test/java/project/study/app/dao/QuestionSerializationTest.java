package project.study.app.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import project.study.app.model.dao.QuestionListConverter;
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

        FreeTextQuestion ftq1 = new FreeTextQuestion("question1", "Rome");
        MultipleChoiceQuestion mcq1 = new MultipleChoiceQuestion(
                "question2",
                new MultipleChoiceQuestion.Answer[]{
                        new MultipleChoiceQuestion.Answer("answer1", false),
                        new MultipleChoiceQuestion.Answer("answer2", true),
                }
        );

        questions.add(ftq1);
        questions.add(mcq1);

        // from list to json (serialize):
        String jsonString = QuestionListConverter.fromList(questions);

        String expectedJsonString = "[{\"type\":\"FreeText\",\"questionText\":\"question1\",\"correctAnswer\":\"Rome\"}," +
                "{\"type\":\"MultipleChoice\",\"questionText\":\"question2\",\"answers\":[{\"answerText\":\"answer1\",\"correctness\":false}," +
                "{\"answerText\":\"answer2\",\"correctness\":true}]}]";

        assertNotNull(jsonString);
        assertEquals(expectedJsonString, jsonString);

        System.out.println(jsonString);
    }

    @Test
    public void from_json_string_to_question_list() {
        String expectedJsonString = "[{\"type\":\"FreeText\",\"questionText\":\"What's the capital of Italy\",\"correctAnswer\":\"Rome\"}," +
                "{\"type\":\"MultipleChoice\",\"questionText\":\"A nice question\",\"answers\":[{\"answerText\":\"Answer 1\",\"correctness\":false}," +
                "{\"answerText\":\"Answer 2\",\"correctness\":true}]}]";


        // Convert the JSON string to a list of questions
        List<Question<?>> questions = QuestionListConverter.toList(expectedJsonString);

        // Assert that the list of questions is not null
        assertNotNull(questions);

        // Assert the size of the list
        assertEquals(2, questions.size());

        // Assert the type and content of the first question
        assertTrue(questions.get(0) instanceof FreeTextQuestion);
        FreeTextQuestion freeTextQuestion = (FreeTextQuestion) questions.get(0);
        assertEquals("What's the capital of Italy", freeTextQuestion.getQuestionText());
        assertEquals("Rome", freeTextQuestion.getCorrectAnswer());

        // Assert the type and content of the second question
        assertTrue(questions.get(1) instanceof MultipleChoiceQuestion);
        MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) questions.get(1);
        assertEquals("A nice question", multipleChoiceQuestion.getQuestionText());
        assertEquals(2, multipleChoiceQuestion.getAnswers().length);
        assertEquals("Answer 1", multipleChoiceQuestion.getAnswers()[0].getAnswerText());
        assertFalse(multipleChoiceQuestion.getAnswers()[0].getCorrectness());
        assertEquals("Answer 2", multipleChoiceQuestion.getAnswers()[1].getAnswerText());
        assertTrue(multipleChoiceQuestion.getAnswers()[1].getCorrectness());
    }
}
