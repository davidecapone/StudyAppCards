package project.study.app;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import project.study.app.model.domain.AnswerFactory;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;

public class QuestionUnitTest {

    private Question question;

    @Before
    public void setUp() {
        question = new Question("The capital of Italy?", null);
    }

    /**
     * Tests setting the question text.
     */
    @Test
    public void testSetQuestionText() {
        assertEquals("The capital of Italy?", question.getText());
    }

    /**
     * Tests modifying the question text.
     */
    @Test
    public void testModifyQuestionText() {
        question.setText("What is the Facebook first name?");
        assertEquals("What is the Facebook first name?", question.getText());
    }

    /**
     * Tests setting the answer to the question.
     */
    @Test
    public void testSetAnswer() {
        question.setAnswer(AnswerFactory.createAnswer("FreeText", "Rome", null));
        assertEquals("Rome", question.getAnswer().getCorrectAnswer());
    }

    /**
     * Tests modifying the answer type for the question.
     */
    @Test
    public void testModifyAnswerType() {
        question.setAnswer(AnswerFactory.createAnswer("FreeText", "Rome", null));

        List<String> possibleAnswers = Arrays.asList("Rome", "New York");
        question.setAnswer(AnswerFactory.createAnswer("MultipleChoice", "Rome", possibleAnswers));

        assertEquals("Rome", question.getAnswer().getCorrectAnswer());
        assertEquals(possibleAnswers, ((MultipleChoiceTextAnswer) question.getAnswer()).getPossibleAnswers());
    }

    /**
     * Tests validating the correct answer for the question.
     */
    @Test
    public void testCorrectAnswer() {
        question.setAnswer(AnswerFactory.createAnswer("FreeText", "Rome", null));
        assertEquals("Rome", question.getAnswer().getCorrectAnswer());
    }
}