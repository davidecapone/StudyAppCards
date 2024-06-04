package project.study.app;
import org.junit.Test;
import static org.junit.Assert.*;
import static project.study.app.MultipleChoiceQuestion.Answer;

public class MultipleChoiceQuestionUnitTest {
    @Test
    public void check_the_correct_answer_is_correct() {
        // question text:
        String question = "Paris is the capital of France";
        // possible answer 1 (the correct one):
        Answer answer1 = new Answer("Yes", true);
        // possible answer 2:
        Answer answer2 = new Answer("No", false);

        Answer[] answers = {answer1, answer2};
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(question, answers);

        // check if passing the correct answer the method returns isCorrect true
        assertEquals(multipleChoiceQuestion.isCorrect(answer1), true);
    }

    @Test
    public void check_the_wrong_answer_is_incorrect() {
        String question = "Rome is the capital of Italy";
        Answer answer1 = new Answer("Yes", true);
        Answer answer2 = new Answer("No", false);
        Answer[] answers = {answer1, answer2};
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(question, answers);

        // the answer2 should be incorrect:
        assertEquals(mcq.isCorrect(answer2), false);
    }

    @Test
    public void check_equality_of_Answer() {
        Answer answer1 = new Answer("Yes", true);
        Answer answer2 = new Answer("Yes", true);
        Answer answer3 = new Answer("No", false);

        // answer1 and answer2 should be equal:
        assertEquals(answer1, answer2);
        // answer1 and answer3 should not be equal:
        assertNotEquals(answer1, answer3);
    }

}
