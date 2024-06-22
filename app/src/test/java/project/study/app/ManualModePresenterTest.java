package project.study.app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import project.study.app.model.domain.QuestionSet;
import project.study.app.presenter.ManualModePresenter;
import project.study.app.service.Callback;
import project.study.app.service.QuestionSetService;
import project.study.app.view.ManualModeView;

@RunWith(MockitoJUnitRunner.class)
public class ManualModePresenterTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private QuestionSetService service;

    @Mock
    private ManualModeView view;

    @InjectMocks
    private ManualModePresenter presenter;

    private QuestionSet questionSet;

    @Before
    public void setUp() {
        questionSet = new QuestionSet("Sample");
    }

    @Test
    public void testLoadAllQuestionSets() {
        // Arrange
        List<QuestionSet> questionSets = new ArrayList<>();
        questionSets.add(new QuestionSet("Sample1"));
        questionSets.add(new QuestionSet("Sample2"));
        MutableLiveData<List<QuestionSet>> liveData = new MutableLiveData<>(questionSets);
        when(service.getAllQuestionSets()).thenReturn(liveData);

        // Act
        presenter.loadAllQuestionSets();

        // Assert
        verify(view).displayQuestionSets(questionSets);
    }

    @Test
    public void testAddNewQuestionSet_Success() {
        // Arrange
        List<QuestionSet> questionSets = new ArrayList<>();
        MutableLiveData<List<QuestionSet>> liveData = new MutableLiveData<>(questionSets);
        when(service.getAllQuestionSets()).thenReturn(liveData);

        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onSuccess();
            return null;
        }).when(service).insert(any(QuestionSet.class), any(Callback.class));

        // Act
        presenter.addNewQuestionSet("Sample");

        // Assert
        verify(service).insert(any(QuestionSet.class), any(Callback.class));
        verify(view).showMessage("Question set added successfully.");
        verify(service, times(2)).getAllQuestionSets(); // To refresh the list after insertion
    }

    @Test
    public void testAddNewQuestionSet_Error() {
        // Arrange
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onError(new Exception("Insert failed"));
            return null;
        }).when(service).insert(any(QuestionSet.class), any(Callback.class));

        // Act
        presenter.addNewQuestionSet("Sample");

        // Assert
        verify(service).insert(any(QuestionSet.class), any(Callback.class));
        verify(view).showMessage("Error adding question set: Insert failed");
    }

    @Test
    public void testDeleteQuestionSet_Success() {
        // Arrange
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onSuccess();
            return null;
        }).when(service).delete(any(QuestionSet.class), any(Callback.class));

        // Act
        presenter.deleteQuestionSet(questionSet);

        // Assert
        verify(service).delete(any(QuestionSet.class), any(Callback.class));
        verify(view).showMessage("Question set deleted successfully.");
        verify(service, times(2)).getAllQuestionSets(); // To refresh the list after deletion
    }

    @Test
    public void testDeleteQuestionSet_Error() {
        // Arrange
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onError(new Exception("Delete failed"));
            return null;
        }).when(service).delete(any(QuestionSet.class), any(Callback.class));

        // Act
        presenter.deleteQuestionSet(questionSet);

        // Assert
        verify(service).delete(any(QuestionSet.class), any(Callback.class));
        verify(view).showMessage("Error deleting question set: Delete failed");
    }

    @Test
    public void testOnQuestionSetSelected() {
        // Act
        presenter.onQuestionSetSelected(questionSet);

        // Assert
        verify(view).navigateToQuestionSetDetails(questionSet);
    }

    @Test
    public void testOnStartExaminationSessionButtonClicked() {
        // Act
        presenter.onStartExaminationSessionButtonClicked(questionSet);

        // Assert
        verify(view).navigateToExaminationSession(questionSet);
    }
}