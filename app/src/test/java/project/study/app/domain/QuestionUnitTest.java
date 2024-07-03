package project.study.app.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        // Initialize a Question instance before each test
        question = new Question("The capital of Italy?", null);
    }

    /**
     * Tests that the initial question text is set correctly.
     * Verifies that the question text matches the expected value.
     */
    @Test
    public void testSetQuestionText() {
        assertEquals("The capital of Italy?", question.getText());
    }

    /**
     * Tests modifying the question text.
     * Verifies that the question text is updated correctly.
     */
    @Test
    public void testModifyQuestionText() {
        question.setText("What is the Facebook first name?");
        assertEquals("What is the Facebook first name?", question.getText());
    }

    /**
     * Tests setting the answer to the question.
     * Verifies that the answer is set correctly.
     */
    @Test
    public void testSetAnswer() {
        question.setAnswer(AnswerFactory.createMultipleChoiceAnswer("Rome", null));
        assertEquals("Rome", question.getAnswer().getCorrectAnswer());
    }

    /**
     * Tests modifying the answer type for the question.
     * Verifies that the answer type and options are updated correctly.
     */
    @Test
    public void testModifyAnswerType() {
        question.setAnswer(AnswerFactory.createMultipleChoiceAnswer("Rome", null));

        List<String> possibleAnswers = Arrays.asList("Rome", "New York");
        question.setAnswer(AnswerFactory.createMultipleChoiceAnswer("Rome", possibleAnswers));

        assertEquals("Rome", question.getAnswer().getCorrectAnswer());
        assertEquals(possibleAnswers, ((MultipleChoiceTextAnswer) question.getAnswer()).getOptions());
    }

    /**
     * Tests validating the correct answer for the question.
     * Verifies that the correct answer is validated successfully.
     */
    @Test
    public void testValidateAnswer() {
        question.setAnswer(AnswerFactory.createFreeTextAnswer("Rome"));
        assertTrue(question.validateAnswer("Rome"));
    }

    /**
     * Tests validating the correct answer for the question with case insensitivity.
     * Verifies that the correct answer is validated successfully regardless of case.
     */
    @Test
    public void testValidateAnswerIgnoreCase() {
        question.setAnswer(AnswerFactory.createFreeTextAnswer("Rome"));
        assertTrue(question.validateAnswer("rome"));
    }
}