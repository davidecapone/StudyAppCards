package project.study.app;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import project.study.app.model.domain.Answer;
import project.study.app.service.QuestionSetService;
import project.study.app.view.ExaminationSessionView;
import project.study.app.presenter.ExaminationSessionPresenter;

import project.study.app.model.domain.QuestionSet;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.FreeTextAnswer;

public class ExaminationSessionPresenterTest {

    @Mock
    private ExaminationSessionView view;

    @Mock
    private QuestionSetService service;

    private ExaminationSessionPresenter presenter;

    private QuestionSet questionSet;

    @Before
    public void setUp() {

        String name = "Sample";

        questionSet = new QuestionSet(name);

        List<Question> questions = Arrays.asList(
                new Question("What is the capital of France?", new FreeTextAnswer("Paris")),
                new Question("What is the capital of Italy?", new FreeTextAnswer("Rome"))
        );

        questionSet.setQuestions(questions);

        MockitoAnnotations.initMocks(this);

        presenter = new ExaminationSessionPresenter(view, service, questionSet);
    }

    @Test
    public void testStartSession() {

        // Act
        presenter.startSession();

        // Verify
        verify(view).showQuestion(any(Question.class));
    }

    @Test
    public void testNextQuestion() {

        // Act
        presenter.nextQuestion();

        // Verify
        verify(view).showQuestion(any(Question.class));
    }

    @Test
    public void testEndSession() {

        // Act
        presenter.endSession();

        // Verify
        verify(view).navigateToManualModeView();
    }

    @Test
    public void testOverallBehaviour() {

        // Act
        presenter.startSession();
        presenter.nextQuestion();
        presenter.nextQuestion();

        // Verify
        verify(view, times(2)).showQuestion(any(Question.class));
        verify(view).navigateToManualModeView();
    }
}

