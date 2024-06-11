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
        Answer answers[] = {
                new Answer("A", true),
                new Answer("B", false),
                new Answer("C", false),
        };
        multipleChoiceQuestion = new MultipleChoiceQuestion("Sample Multiple Choice Question", answers);
    }

    @Test
    public void test_add_question() {

        // add the free text question to the question set:
        questionSet.addQuestion(freeTextQuestion);

        // retrieve the questions from the question set:
        List<Question<?>> questions = questionSet.getQuestions();

        // assert that the number of questions is 1:
        assertEquals(1, questionSet.getQuestions().size());

        // assert that the first question is the free text question that was added:
        assertEquals(freeTextQuestion, questions.get(0));
    }

    @Test
    public void test_add_multiple_questions() {

        // add both questions to the question set:
        questionSet.addQuestion(freeTextQuestion);
        questionSet.addQuestion(multipleChoiceQuestion);

        // retrieve the questions from the question set:
        List<Question<?>> questions = questionSet.getQuestions();

        // assert that the number of questions is 2:
        assertEquals(2, questionSet.getQuestions().size());

        // assert that the first question is the free text question that was added:
        assertEquals(freeTextQuestion, questions.get(0));

        // assert that the second question is the multiple choice question that was added:
        assertEquals(multipleChoiceQuestion, questions.get(1));

    }

    @Test
    public void test_remove_question() {

        // add both questions to the question set:
        questionSet.addQuestion(freeTextQuestion);
        questionSet.addQuestion(multipleChoiceQuestion);

        // remove the free text question:
        questionSet.removeQuestion(freeTextQuestion);

        // retrieve the questions from the question set:
        List<Question<?>> questions = questionSet.getQuestions();

        // assert that the number of questions is 1:
        assertEquals(1, questions.size());

        // assert that the first question is the multiple choice question that was added:
        assertEquals(multipleChoiceQuestion, questions.get(0));
    }

    @Test
    public void test_getRandomQuestion() {
        // add both questions to the question set:
        questionSet.addQuestion(freeTextQuestion);
        questionSet.addQuestion(multipleChoiceQuestion);

        // retrieve the questions from the question set:
        List<Question<?>> questions = questionSet.getQuestions();

        // retrieve a random question from the question set:
        Question<?> randomQuestion = questionSet.getRandomQuestion();

        // assert that the random question is one of the questions in the question set:
        assertTrue(questions.contains(randomQuestion));
    }

    @Test
    public void test_getRandomizedQuestionList() {

        // add both questions to the question set:
        questionSet.addQuestion(freeTextQuestion);
        questionSet.addQuestion(multipleChoiceQuestion);

        // add more questions to the question set:
        questionSet.addQuestion(new FreeTextQuestion("Sample Free Text Question 2", "Sample Answer 2"));
        questionSet.addQuestion(new FreeTextQuestion("Sample Free Text Question 3", "Sample Answer 3"));
        questionSet.addQuestion(new FreeTextQuestion("Sample Free Text Question 4", "Sample Answer 4"));
        questionSet.addQuestion(new FreeTextQuestion("Sample Free Text Question 5", "Sample Answer 5"));

        // retrieve a randomized list of questions from the question set:
        List<Question<?>> randomizedQuestions = questionSet.getRandomizedQuestionList();

        // assert that the number of questions in the randomized list is the same as the number of questions in the question set:
        assertEquals(questionSet.getQuestions().size(), randomizedQuestions.size());

        // assert that the questions in the randomized list are the same as the questions in the question set:
        for (int i = 0; i < questionSet.getQuestions().size(); i++) {
            assertTrue(randomizedQuestions.contains(questionSet.getQuestions().get(i)));
        }
    }
}
