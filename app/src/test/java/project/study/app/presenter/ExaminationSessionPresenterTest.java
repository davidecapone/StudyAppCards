package project.study.app.presenter;

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
import project.study.app.model.domain.QuestionSet;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.FreeTextAnswer;

/**
 * Test class for the ExaminationSessionPresenter
 */
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
    /**
     * Test that the startExamination method calls the service to get the question set
     */
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
    /**
     * Test that the checkAnswer method correctly validates the answer
     */
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
        presenter.validateAnswer("4");
        verify(view).showCorrectAnswerFeedback();
    }
    /**
     * Test that the checkAnswer method correctly validates the answer
     */
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
        presenter.validateAnswer("5");
        verify(view).showIncorrectAnswerFeedback();
    }
    /**
     * Test that the checkAnswer method correctly navigates to the next question
     */
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
        presenter.validateAnswer("4");
        verify(view).showCorrectAnswerFeedback();
        verify(view).navigateToManualModeActivity();
    }
    /**
     * Test startExaminationSession with empty question set
     */
    @Test
    public void testStartExaminationSession_EmptyQuestionSet() {
        // Initialize an empty questionSet
        String questionSetName = "Test Set";
        QuestionSet questionSet = new QuestionSet(questionSetName);
        // Start the examination session
        presenter.startExamination(questionSetName);
        // Verify that the service was called to get the question set
        verify(service).getQuestionSetByName(eq(questionSetName), callbackCaptor.capture());
        callbackCaptor.getValue().onSuccess(questionSet);
        // Verify that the view was called to show the message
        verify(view).showMessage(any());
        // Verify that the view was called to navigate to the manual mode
        verify(view).navigateToManualModeActivity();
    }
}

