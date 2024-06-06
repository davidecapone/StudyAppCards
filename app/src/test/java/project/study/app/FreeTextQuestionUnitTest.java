package project.study.app;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import project.study.app.model.FreeTextQuestion;

/**
 * Unit tests for the FreeTextQuestion class.
 */
public class FreeTextQuestionUnitTest {

    private String questionText;

    private FreeTextQuestion freeTextQuestion;

    @Before
    public void set_up() {

        // set the question text
        questionText = "How many days are there in a week?";

        // create a new FreeTextQuestion object
        freeTextQuestion = new FreeTextQuestion(questionText, "7");
    }

    @Test
    public void check_the_correct_answer_is_correct() {

        String correctAnswer = "7";

        // check if passing the correct answer the method returns true
        assertTrue(freeTextQuestion.checkAnswer(correctAnswer));
    }

    @Test
    public void check_the_incorrect_answer_is_incorrect() {

        String inCorrectAnswer = "8";

        // check if passing the incorrect answer the method returns false
        assertFalse(freeTextQuestion.checkAnswer(inCorrectAnswer));
    }
}
