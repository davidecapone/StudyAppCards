package project.study.app.domain;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;

public class QuestionSetUnitTest {

    private QuestionSet questionSet;

    @Before
    public void set_up() {

        // create a question set:
        questionSet = new QuestionSet("Sample Question Set");
    }

    @Test
    public void add_question_to_questionSet() {

        Question question = new Question("The capital of Italy", new FreeTextAnswer("Rome"));
        questionSet.addQuestion(question);

        List<Question> questions = questionSet.getAllQuestions(); // retrieve the questions

        // assert that the number of questions is 1
        assertEquals(1, questionSet.getAllQuestions().size());
        // assert that the first question is the free text question that was added:
        assertEquals(question, questions.get(0));
    }

    @Test
    public void add_multiple_questions_works() {

        // add both questions to the question set:
        Question question1 = new Question("The capital of Italy", new FreeTextAnswer("Rome"));
        Question question2 = new Question("The capital of Germany", new FreeTextAnswer("Berlin"));

        questionSet.addQuestion(question1);
        questionSet.addQuestion(question2);

        List<Question> questions = questionSet.getAllQuestions();

        // assert that the number of questions is 2
        assertEquals(2, questionSet.getAllQuestions().size());

        // assert that the first question is the free text question that was added:
        assertEquals(question1, questions.get(0));

        // assert that the second question is the multiple choice question that was added:
        assertEquals(question2, questions.get(1));
    }

    @Test
    public void remove_single_question() {

        Question question1 = new Question("The capital of Italy", new FreeTextAnswer("Rome"));
        Question question2 = new Question("The capital of Germany", new FreeTextAnswer("Berlin"));

        questionSet.addQuestion(question1);
        questionSet.addQuestion(question2);

        // remove the first question:
        questionSet.removeQuestion(question1);

        // retrieve the questions from the question set:
        List<Question> questions = questionSet.getAllQuestions();

        // assert that the number of questions is 1:
        assertEquals(1, questions.size());

        assertEquals(question2, questions.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void null_question_cannot_be_add() throws IllegalArgumentException {
        questionSet.addQuestion(null);
    }
}
