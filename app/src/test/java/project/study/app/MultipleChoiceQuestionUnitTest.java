package project.study.app;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static project.study.app.model.domain.MultipleChoiceQuestion.Answer;
import project.study.app.model.domain.MultipleChoiceQuestion;

/**
 * Unit tests for the MultipleChoiceQuestion class.
 */
public class MultipleChoiceQuestionUnitTest implements QuestionUnitTest{

    private String questionText;
    private Answer[] answers;
    private MultipleChoiceQuestion multipleChoiceQuestion;

    @Before
    public void set_up() {

        // set the question text
        questionText = "Paris is the capital of France";

        // set the possible answers for the multiple choice question
        Answer answer1 = new Answer("Yes", true);
        Answer answer2 = new Answer("No", false);
        answers = new Answer[]{answer1, answer2};

        // create a new MultipleChoiceQuestion object
        multipleChoiceQuestion = new MultipleChoiceQuestion(questionText, answers);
    }

    @Override
    @Test
    public void correct_answer_is_correct() {

        // set the correct answer
        Answer correctAnswer = answers[0];

        // check if passing the correct answer to the method returns isCorrect true
        assertEquals(multipleChoiceQuestion.checkAnswer(correctAnswer), true);
    }

    @Override
    @Test
    public void incorrect_answer_is_incorrect() {
        // set the incorrect answer
        Answer incorrectAnswer = answers[1];

        // check if passing the wrong answer the method returns isCorrect false
        assertEquals(multipleChoiceQuestion.checkAnswer(incorrectAnswer), false);
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void correct_answer_is_null() throws IllegalArgumentException {

        // create a new MultipleChoiceQuestion object with a null answer
        multipleChoiceQuestion = new MultipleChoiceQuestion(questionText, null);
    }

    @Override
    public void correct_answer_is_empty() throws IllegalArgumentException {

        // create a new MultipleChoiceQuestion object with a 0 length answer
        multipleChoiceQuestion = new MultipleChoiceQuestion(questionText, new Answer[0]);
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void questionText_is_null() throws IllegalArgumentException {

        // create a new MultipleChoiceQuestion object with a null question text
        multipleChoiceQuestion = new MultipleChoiceQuestion(null, answers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void questionText_is_empty() throws IllegalArgumentException {

        // create a new MultipleChoiceQuestion object with a empty question text
        multipleChoiceQuestion = new MultipleChoiceQuestion("", answers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void answer_not_in_array() throws IllegalArgumentException {

        multipleChoiceQuestion.checkAnswer(
                new Answer("Bla bla bla", false)
        );
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void given_answer_is_null() throws IllegalArgumentException {

        // create a new MultipleChoiceQuestion object with a null answer
        multipleChoiceQuestion.checkAnswer(null);
    }




}
