package project.study.app;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;

public class QuestionSetUnitTest {

    private QuestionSet questionSet;

    @Before
    public void setUp() {
        questionSet = new QuestionSet("Sample Question Set");
    }

    @Test
    public void testAddQuestion() {
        Question question = new Question("The capital of Italy", new FreeTextAnswer("Rome"));
        questionSet.addQuestion(question);

        List<Question> questions = questionSet.getQuestions();

        assertEquals(1, questions.size());
        assertEquals(question, questions.get(0));
    }

    @Test
    public void testAddMultipleQuestions() {
        Question question1 = new Question("The capital of Italy", new FreeTextAnswer("Rome"));
        Question question2 = new Question("The capital of Germany", new FreeTextAnswer("Berlin"));

        questionSet.addQuestion(question1);
        questionSet.addQuestion(question2);

        List<Question> questions = questionSet.getQuestions();

        assertEquals(2, questions.size());
        assertEquals(question1, questions.get(0));
        assertEquals(question2, questions.get(1));
    }

    @Test
    public void testRemoveQuestion() {
        Question question = new Question("The capital of Italy", new FreeTextAnswer("Rome"));
        questionSet.addQuestion(question);
        questionSet.removeQuestion(question);

        List<Question> questions = questionSet.getQuestions();

        assertEquals(0, questions.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullQuestion() {
        questionSet.addQuestion(null);
    }
}