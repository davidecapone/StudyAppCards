package project.study.app;
import org.junit.Test;
import static org.junit.Assert.*;

import project.study.app.model.FreeTextQuestion;

public class FreeTextQuestionUnitTest {

    @Test
    public void check_the_correct_answer_is_correct() {
        String question = "How many days are there in a week?";
        String correctAnswer = "7";

        FreeTextQuestion freeTextQuestion = new FreeTextQuestion(question, correctAnswer);

        assertTrue(freeTextQuestion.checkAnswer("7"));
    }

    @Test
    public void check_the_incorrect_answer_is_incorrect() {
        String question = "How many days are there in a week?";
        String correctAnswer = "7";

        FreeTextQuestion freeTextQuestion = new FreeTextQuestion(question, correctAnswer);

        assertFalse(freeTextQuestion.checkAnswer("8"));
    }
}
