package project.study.app;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import project.study.app.model.domain.AnswerFactory;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;

public class QuestionSetUnitTest {

    private QuestionSet questionSet;

    @Before
    public void setUp() {
        questionSet = new QuestionSet("Sample Question Set");
    }

    /**
     * Tests adding a question to the question set.
     */
    @Test
    public void testAddQuestion() {
        Question question = new Question("The capital of Italy",
                AnswerFactory.createAnswer("FreeText", "Rome", null));
        questionSet.addQuestion(question);

        List<Question> questions = questionSet.getQuestions();

        assertEquals(1, questions.size());
        assertEquals(question, questions.get(0));
    }

    /**
     * Tests adding multiple questions to the question set.
     */
    @Test
    public void testAddMultipleQuestions() {
        Question question1 = new Question("The capital of Italy",
                AnswerFactory.createAnswer("FreeText", "Rome", null));
        Question question2 = new Question("The capital of Germany",
                AnswerFactory.createAnswer("FreeText", "Berlin", null));

        questionSet.addQuestion(question1);
        questionSet.addQuestion(question2);

        List<Question> questions = questionSet.getQuestions();

        assertEquals(2, questions.size());
        assertEquals(question1, questions.get(0));
        assertEquals(question2, questions.get(1));
    }

    /**
     * Tests removing a question from the question set.
     */
    @Test
    public void testRemoveQuestion() {
        Question question = new Question("The capital of Italy",
                AnswerFactory.createAnswer("FreeText", "Rome", null));
        questionSet.addQuestion(question);
        questionSet.removeQuestion(question);

        List<Question> questions = questionSet.getQuestions();

        assertEquals(0, questions.size());
    }

    /**
     * Tests that adding a null question throws an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullQuestion() {
        questionSet.addQuestion(null);
    }
}