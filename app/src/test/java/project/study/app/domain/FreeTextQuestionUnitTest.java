package project.study.app.domain;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import project.study.app.model.domain.FreeTextQuestion;

/**
 * Unit tests for the FreeTextQuestion class.
 */
public class FreeTextQuestionUnitTest  implements QuestionUnitTest{

    private String questionText;
    private FreeTextQuestion freeTextQuestion;

    @Before
    public void set_up() {
        // set the question text
        questionText = "How many days are there in a week?";
        // create a new FreeTextQuestion object
        freeTextQuestion = new FreeTextQuestion(questionText, "7");
    }

    @Override
    @Test
    public void correct_answer_is_correct() {
        String correctAnswer = "7";

        // check if passing the correct answer the method returns true
        assertTrue(freeTextQuestion.checkAnswer(correctAnswer));
    }

    @Override
    @Test
    public void incorrect_answer_is_incorrect() {
        String inCorrectAnswer = "8";

        // check if passing the incorrect answer the method returns false
        assertFalse(freeTextQuestion.checkAnswer(inCorrectAnswer));
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_answer_is_null() throws IllegalArgumentException{

        // check if passing a null answer the method throws an exception
        freeTextQuestion.checkAnswer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_answer_is_empty() throws IllegalArgumentException {

        // check if passing an empty answer the method throws an exception
        freeTextQuestion.checkAnswer("");
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void questionText_is_null() throws IllegalArgumentException{

        // create a new FreeTextQuestion object with a null text
        new FreeTextQuestion(null, "7");
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void questionText_is_empty() throws IllegalArgumentException{

        // create a new FreeTextQuestion object with an empty text
        new FreeTextQuestion("", "7");
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void given_answer_is_null() throws IllegalArgumentException {
        // check if passing an empty answer the method throws an exception
        freeTextQuestion.checkAnswer(null);
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void correct_answer_is_null() throws IllegalArgumentException{

        // create a new FreeTextQuestion object with a null correct answer
        new FreeTextQuestion("How many days are there in a week?", null);
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void correct_answer_is_empty() throws IllegalArgumentException{

        // create a new FreeTextQuestion object with an empty correct answer
        new FreeTextQuestion("How many days are there in a week?", "");
    }
}
