package project.study.app;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import project.study.app.service.interfaces.QuestionSetService;
import project.study.app.service.interfaces.SingleItemCallback;
import project.study.app.view.interfaces.ExaminationSessionView;
import project.study.app.presenter.ExaminationSessionPresenter;

import project.study.app.model.domain.QuestionSet;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.FreeTextAnswer;

public class ExaminationSessionPresenterTest {
    @Mock
    private QuestionSetService service;

    @Mock
    private ExaminationSessionView view;

    @Captor
    private ArgumentCaptor<SingleItemCallback<QuestionSet>> callbackCaptor;

    private ExaminationSessionPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        presenter = new ExaminationSessionPresenter(service, view);
    }

    @Test
    public void testStartExaminationSession_DisplaysFirstQuestion() {
        String questionSetName = "Test Set";
        Question question1 = new Question("What is 2+2?", null);
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        QuestionSet questionSet = new QuestionSet(questionSetName);
        questionSet.setQuestions(questions);

        presenter.startExamination(questionSetName);

        verify(service).getQuestionSetByName(eq(questionSetName), callbackCaptor.capture());
        callbackCaptor.getValue().onSuccess(questionSet);

        verify(view).displayQuestion(question1);
    }

    @Test
    public void testCheckAnswer_CorrectAnswer() {
        String questionSetName = "Test Set";
        Question question1 = new Question(
                "What is 2+2?",
                new FreeTextAnswer("4"));
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        QuestionSet questionSet = new QuestionSet(questionSetName);
        questionSet.setQuestions(questions);

        presenter.startExamination(questionSetName);

        verify(service).getQuestionSetByName(eq(questionSetName), callbackCaptor.capture());
        callbackCaptor.getValue().onSuccess(questionSet);

        presenter.checkAnswer("4");

        verify(view).showCorrectAnswerFeedback();
    }

    @Test
    public void testCheckAnswer_IncorrectAnswer() {
        String questionSetName = "Test Set";
        Question question1 = new Question(
                "What is 2+2?",
                new FreeTextAnswer("4"));
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        QuestionSet questionSet = new QuestionSet(questionSetName);
        questionSet.setQuestions(questions);

        presenter.startExamination(questionSetName);

        verify(service).getQuestionSetByName(eq(questionSetName), callbackCaptor.capture());
        callbackCaptor.getValue().onSuccess(questionSet);

        presenter.checkAnswer("5");

        verify(view).showIncorrectAnswerFeedback();
    }

    @Test
    public void testCheckAnswer_EndOfQuestionSet() {
        String questionSetName = "Test Set";
        Question question1 = new Question(
                "What is 2+2?",
                new FreeTextAnswer("4"));
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        QuestionSet questionSet = new QuestionSet(questionSetName);
        questionSet.setQuestions(questions);

        presenter.startExamination(questionSetName);

        verify(service).getQuestionSetByName(eq(questionSetName), callbackCaptor.capture());
        callbackCaptor.getValue().onSuccess(questionSet);

        presenter.checkAnswer("4");

        verify(view).showCorrectAnswerFeedback();
        // back to the manual mode activity:
        verify(view).navigateToManualMode();
    }
}

