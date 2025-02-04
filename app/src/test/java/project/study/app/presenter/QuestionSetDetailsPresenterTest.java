package project.study.app.presenter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Collections;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.service.interfaces.Callback;
import project.study.app.service.interfaces.Service;
import project.study.app.service.interfaces.SingleItemCallback;
import project.study.app.view.interfaces.QuestionSetDetailsView;

/**
 * Test class for the QuestionSetDetailsPresenter
 */
public class QuestionSetDetailsPresenterTest {
    @Mock
    private Service service;
    @Mock
    private QuestionSetDetailsView view;
    @InjectMocks
    private QuestionSetDetailsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    /**
     * Test that the loadQuestionSet method loads the questions successfully
     */
    @Test
    public void testLoadQuestions_Success() {
        // Arrange
        String questionSetName = "Sample Set";
        QuestionSet questionSet = new QuestionSet(questionSetName);
        questionSet.setQuestions(new ArrayList<>(Collections.singletonList(
                new Question("What is the capital of France?", new FreeTextAnswer("Paris")))));

        doAnswer(invocation -> {
            SingleItemCallback<QuestionSet> callback = invocation.getArgument(1);
            callback.onSuccess(questionSet);
            return null;
        }).when(service).getQuestionSetByName(anyString(), any(SingleItemCallback.class));
        // Act
        presenter.loadQuestionSet(questionSetName);
        // Assert
        verify(view).displayQuestions(questionSet.getQuestions());
    }
    /**
     * Test that the loadQuestionSet method shows an error message when the question set is not found
     */
    @Test
    public void testLoadQuestions_Error() {
        // Arrange
        String questionSetName = "Sample Set";
        doAnswer(invocation -> {
            SingleItemCallback<QuestionSet> callback = invocation.getArgument(1);
            callback.onError(new Exception("Question set not found"));
            return null;
        }).when(service).getQuestionSetByName(anyString(), any(SingleItemCallback.class));
        // Act
        presenter.loadQuestionSet(questionSetName);
        // Assert
        verify(view).showMessage("Error loading question set: Question set not found");
    }
    /**
     * Test that the addQuestion method adds a question successfully
     */
    @Test
    public void testAddQuestion_Success() {
        // Arrange
        String questionSetName = "Sample Set";
        QuestionSet questionSet = new QuestionSet(questionSetName);
        questionSet.setQuestions(new ArrayList<>());
        Question newQuestion = new Question("What is the capital of Germany?", null);
        doAnswer(invocation -> {
            SingleItemCallback<QuestionSet> callback = invocation.getArgument(1);
            callback.onSuccess(questionSet);
            return null;
        }).when(service).getQuestionSetByName(anyString(), any(SingleItemCallback.class));
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onSuccess();
            return null;
        }).when(service).update(any(QuestionSet.class), any(Callback.class));
        presenter.loadQuestionSet(questionSetName);
        // Act
        presenter.addQuestion(newQuestion);
        // Assert
        verify(service).update(any(QuestionSet.class), any(Callback.class));
        // Verify that displayQuestions is called twice: once during load and once during add
        verify(view, times(2)).displayQuestions(anyList());
        verify(view).showMessage("Question added successfully.");
    }
    /**
     * Test that the addQuestion method shows an error message when adding a question fails
     */
    @Test
    public void testAddQuestion_Error() {
        // Arrange
        String questionSetName = "Sample Set";
        QuestionSet questionSet = new QuestionSet(questionSetName);
        questionSet.setQuestions(new ArrayList<>());
        Question newQuestion = new Question("What is the capital of Germany?", null);
        doAnswer(invocation -> {
            SingleItemCallback<QuestionSet> callback = invocation.getArgument(1);
            callback.onSuccess(questionSet);
            return null;
        }).when(service).getQuestionSetByName(anyString(), any(SingleItemCallback.class));
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onError(new Exception("Update failed"));
            return null;
        }).when(service).update(any(QuestionSet.class), any(Callback.class));
        presenter.loadQuestionSet(questionSetName);
        // Act
        presenter.addQuestion(newQuestion);
        // Assert
        verify(service).update(any(QuestionSet.class), any(Callback.class));
        verify(view, times(1)).displayQuestions(anyList());
        verify(view).showMessage("Error adding question: Update failed");
    }
}