package project.study.app;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static project.study.app.model.domain.MultipleChoiceQuestion.Answer;

import java.util.List;

import project.study.app.model.domain.FreeTextQuestion;
import project.study.app.model.domain.MultipleChoiceQuestion;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;

public class QuestionSetUnitTest {

    private QuestionSet questionSet;
    private FreeTextQuestion freeTextQuestion;
    private MultipleChoiceQuestion multipleChoiceQuestion;

    @Before
    public void set_up() {

        // create a question set:
        questionSet = new QuestionSet("Sample Question Set");

        // create a free text question:
        freeTextQuestion = new FreeTextQuestion("Sample Free Text Question", "Sample Answer");

        // create a multiple choice question and its answers:
        Answer[] answers = {
                new Answer("A", true),
                new Answer("B", false),
                new Answer("C", false),
        };
        multipleChoiceQuestion = new MultipleChoiceQuestion("Sample Multiple Choice Question", answers);
    }

    @Test
    public void add_new_question_works() {

        // add the free text question to the question set:
        questionSet.addQuestion(freeTextQuestion);

        // retrieve the questions from the question set:
        List<Question<?>> questions = questionSet.getAllQuestions();

        // assert that the number of questions is 1:
        assertEquals(1, questionSet.getAllQuestions().size());

        // assert that the first question is the free text question that was added:
        assertEquals(freeTextQuestion, questions.get(0));
    }

    @Test
    public void add_multiple_questions_works() {

        // add both questions to the question set:
        questionSet.addQuestion(freeTextQuestion);
        questionSet.addQuestion(multipleChoiceQuestion);

        // retrieve the questions from the question set:
        List<Question<?>> questions = questionSet.getAllQuestions();

        // assert that the number of questions is 2:
        assertEquals(2, questionSet.getAllQuestions().size());

        // assert that the first question is the free text question that was added:
        assertEquals(freeTextQuestion, questions.get(0));

        // assert that the second question is the multiple choice question that was added:
        assertEquals(multipleChoiceQuestion, questions.get(1));

    }

    @Test
    public void remove_question_works() {

        // add both questions to the question set:
        questionSet.addQuestion(freeTextQuestion);
        questionSet.addQuestion(multipleChoiceQuestion);

        // remove the free text question:
        questionSet.removeQuestion(freeTextQuestion);

        // retrieve the questions from the question set:
        List<Question<?>> questions = questionSet.getAllQuestions();

        // assert that the number of questions is 1:
        assertEquals(1, questions.size());

        // assert that the first question is the multiple choice question that was added:
        assertEquals(multipleChoiceQuestion, questions.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_null_question_throws_exception() throws IllegalArgumentException {
        questionSet.addQuestion(null);
    }
}
