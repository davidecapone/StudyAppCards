package project.study.app;
import org.junit.Test;

/**
 * This interface defines all the methods needed to perform the common tests
 * for different types of questions (FreeText, MultipleChoice)
 */

public interface QuestionUnitTest {

    @Test
    void correct_answer_is_correct();

    @Test
    void incorrect_answer_is_incorrect();

    @Test(expected = IllegalArgumentException.class)
    void correct_answer_is_null() throws IllegalArgumentException;

    @Test(expected = IllegalArgumentException.class)
    void correct_answer_is_empty() throws IllegalArgumentException;

    @Test(expected = IllegalArgumentException.class)
    void questionText_is_null() throws IllegalArgumentException;

    @Test(expected = IllegalArgumentException.class)
    void questionText_is_empty() throws IllegalArgumentException;

    @Test(expected = IllegalArgumentException.class)
    void given_answer_is_null() throws IllegalArgumentException;

}
