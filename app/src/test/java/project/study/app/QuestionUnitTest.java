package project.study.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionUnitTest {
    @Test
    public void question_isCorrect() {
        String question = "Paris is the capital of France";
        String answer1 = "True";
        String answer2 = "False";
        String correctAnswer = "True";

        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(question, answer1, answer2, correctAnswer);

        assertEquals(multipleChoiceQuestion.getCorrectAnswer(), "True");
    }
}
