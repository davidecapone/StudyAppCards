package project.study.app;

import org.junit.Test;

import static org.junit.Assert.*;
import static project.study.app.MultipleChoiceQuestion.Answer;

public class QuestionUnitTest {
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
    public void check_questionText_getter() {
        String question = "Rome is the capital of Italy";
        Answer answer1 = new Answer("Yes", true);
        Answer answer2 = new Answer("No", false);
        Answer[] answers = {answer1, answer2};
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(question, answers);

        assertEquals(mcq.getQuestionText(), question);
    }

    @Test
    public void check_setQuestionText() {
        String question = "Rome is the capital of Italy";
        Answer answer1 = new Answer("Yes", true);
        Answer answer2 = new Answer("No", false);
        Answer[] answers = {answer1, answer2};
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(question, answers);

        String newQuestion = "Is Rome the capital of Italy?";
        mcq.setQuestionText(newQuestion);

        assertEquals(mcq.getQuestionText(), newQuestion);
    }

    @Test
    public void check_equality_of_Answer() {
        Answer answer1 = new Answer("Yes", true);
        Answer answer2 = new Answer("Yes", true);
        Answer answer3 = new Answer("No", false);

        assertEquals(answer1, answer2);
        assertNotEquals(answer1, answer3);
    }

    @Test
    public void check_getAnswerText() {
        Answer answer1 = new Answer("Yes", true);

        assertEquals(answer1.getAnswerText(), "Yes");
    }

    @Test
    public void check_setAnswerText() {
        Answer answer1 = new Answer("Yes", true);
        answer1.setAnswerText("No");

        assertEquals(answer1.getAnswerText(), "No");
    }

    @Test
    public void check_setCorrectness() {
        Answer answer1 = new Answer("Yes", true);
        answer1.setCorrectness(false);

        assertEquals(answer1.getCorrectness(), false);
    }

    @Test
    public void getAnswers() {
        Answer answer1 = new Answer("Yes", true);
        Answer answer2 = new Answer("No", false);
        Answer[] answers = {answer1, answer2};
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion("Rome is the capital of Italy", answers);

        assertArrayEquals(mcq.getAnswers(), answers);
    }
}
