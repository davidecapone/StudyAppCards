package project.study.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;

public class QuestionUnitTest {

    private Question question;

    @Test
    public void questionText_can_be_set() {
        question = new Question("The capital of Italy?", null);
        assertEquals("The capital of Italy?", question.getText());
    }

    @Test
    public void answer_can_be_modified() {
        question = new Question("The capital of Italy?", null);
        question.setText("What is the Facebook first name?");
        assertEquals("What is the Facebook first name?", question.getText());
    }

    @Test
    public void answer_can_be_set() {
        FreeTextAnswer freeTxtAnswer = new FreeTextAnswer("Rome");
        question = new Question(
                "The capital of Italy?",
                freeTxtAnswer
        );

        assertEquals(question.getAnswer(), freeTxtAnswer);
    }

    @Test
    public void answer_mode_can_be_modify() {
        FreeTextAnswer freeTxtAnswer = new FreeTextAnswer("Rome");
        question = new Question(
                "The capital of Italy?",
                freeTxtAnswer
        );

        List<String> possibleAnswers = new ArrayList<>();
        possibleAnswers.add("Rome");
        possibleAnswers.add("New York");
        MultipleChoiceTextAnswer newAnswer = new MultipleChoiceTextAnswer(
                possibleAnswers,
                "Rome"
        );

        // set a different answer for the question:
        question.setAnswer(newAnswer);

        assertEquals(question.getAnswer(), newAnswer);
    }

   @Test
   public void check_correct_answer_for_question() {
       question = new Question("The capital of Italy?", new FreeTextAnswer("Rome"));
       assertEquals(question.getAnswer().getCorrectAnswer(), "Rome");
   }
}