package project.study.app;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import project.study.app.model.domain.AnswerFactory;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.presenter.QuestionSetDetailsPresenter;
import project.study.app.service.QuestionSetService;
import project.study.app.view.QuestionSetDetailsView;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import java.util.Arrays;
import java.util.List;


/**
 * Test class for QuestionSetDetailsPresenter
 */
public class QuestionSetDetailsPresenterTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private QuestionSetService service;
    private QuestionSetDetailsView view;
    private QuestionSetDetailsPresenter presenter;

    @Before
    public void setUp() {
        service = mock(QuestionSetService.class);
        view = mock(QuestionSetDetailsView.class);
        presenter = new QuestionSetDetailsPresenter(service, view);
    }

    @Test
    public void testLoadQuestionSet() {

        String name = "Sample";
        QuestionSet questionSet = new QuestionSet(name);
        List<Question> questions = Arrays.asList(
                new Question("What is the capital of France?", new FreeTextAnswer("Paris")),
                new Question("What is the capital of Italy?", new FreeTextAnswer("Rome"))
        );
        questionSet.setQuestions(questions);
        when(service.getQuestionSetByName(name)).thenReturn(questionSet);

        // Act
        presenter.loadQuestionSet(name);

        verify(view).setQuestionSetName(name);
        verify(view, times(2)).displayQuestions(questions);
    }

    @Test
    public void testAddQuestion() {
        // Arrange
        String name = "Sample";
        QuestionSet questionSet = new QuestionSet(name);
        when(service.getQuestionSetByName(name)).thenReturn(questionSet);

        presenter.loadQuestionSet(name);

        Question question = new Question("What is the capital of Germany?", new FreeTextAnswer("Berlin"));

        // Act
        presenter.addQuestion(question);

        // Assert
        verify(service).update(questionSet);
        verify(view, times(2)).displayQuestions(questionSet.getQuestions());
    }

    @Test
    public void testSaveQuestionSet() {
        // Arrange
        String name = "Sample";
        QuestionSet questionSet = new QuestionSet(name);
        when(service.getQuestionSetByName(name)).thenReturn(questionSet);
        presenter.loadQuestionSet(name);
        String newName = "New Sample";

        // Act
        presenter.saveQuestionSet(newName);

        // Assert
        verify(service).update(questionSet);
        verify(view).showMessage("Question set updated successfully.");
    }
}
