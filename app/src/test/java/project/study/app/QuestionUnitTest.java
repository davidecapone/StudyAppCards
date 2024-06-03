package project.study.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionUnitTest {
    @Test
    public void answer_isCorrect() {
        String question = "Paris is the capital of France";

        MultipleChoiceQuestion.Answer answer1 = new MultipleChoiceQuestion.Answer("True", true);

        MultipleChoiceQuestion.Answer answer2 = new MultipleChoiceQuestion.Answer("False", false);

        MultipleChoiceQuestion.Answer[] answers = {answer1, answer2};

        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(question, answers);

        assertEquals(multipleChoiceQuestion.checkAnswerCorrectness(answer1), true);
    }
}
