package project.study.app;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;

public class QuestionUnitTest {

    private Question question;

    @Before
    public void setUp() {
        question = new Question("The capital of Italy?", null);
    }

    @Test
    public void testSetQuestionText() {
        assertEquals("The capital of Italy?", question.getText());
    }

    @Test
    public void testModifyQuestionText() {
        question.setText("What is the Facebook first name?");
        assertEquals("What is the Facebook first name?", question.getText());
    }

    @Test
    public void testSetAnswer() {
        FreeTextAnswer freeTxtAnswer = new FreeTextAnswer("Rome");
        question = new Question("The capital of Italy?", freeTxtAnswer);

        assertEquals(question.getAnswer(), freeTxtAnswer);
    }

    @Test
    public void testModifyAnswerType() {
        FreeTextAnswer freeTxtAnswer = new FreeTextAnswer("Rome");
        question = new Question("The capital of Italy?", freeTxtAnswer);

        List<String> possibleAnswers = new ArrayList<>();
        possibleAnswers.add("Rome");
        possibleAnswers.add("New York");
        MultipleChoiceTextAnswer newAnswer = new MultipleChoiceTextAnswer(possibleAnswers, "Rome");

        question.setAnswer(newAnswer);

        assertEquals(question.getAnswer(), newAnswer);
    }

    @Test
    public void testCorrectAnswer() {
        question = new Question("The capital of Italy?", new FreeTextAnswer("Rome"));
        assertEquals("Rome", question.getAnswer().getCorrectAnswer());
    }
}