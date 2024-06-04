package project.study.app;

import org.junit.Test;

import static org.junit.Assert.*;
import static project.study.app.MultipleChoiceQuestion.Answer;

public class QuestionUnitTest {
    @Test
    public void check_the_answer_is_correct() {
        // question text:
        String question = "Paris is the capital of France";
        // possible answer 1 (the correct one):
        Answer answer1 = new Answer("Yes", true);
        // possible answer 2:
        Answer answer2 = new Answer("No", false);

        Answer[] answers = {answer1, answer2};
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(question, answers);

        // check if passing the correct answer the method returns isCorrect = true
        assertEquals(multipleChoiceQuestion.isCorrect(answer1), true);
    }
}
