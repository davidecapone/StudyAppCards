package project.study.app;

import static org.mockito.Mockito.*;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import project.study.app.model.domain.QuestionSet;
import project.study.app.presenter.ManualModePresenter;
import project.study.app.service.interfaces.Callback;
import project.study.app.service.interfaces.QuestionSetService;
import project.study.app.view.interfaces.ManualModeView;

import java.util.Arrays;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ManualModePresenterTest {

    @Mock
    private QuestionSetService service;

    @Mock
    private ManualModeView view;

    @InjectMocks
    private ManualModePresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadAllQuestionSets() {
        // Arrange
        MutableLiveData<List<QuestionSet>> liveData = new MutableLiveData<>();
        List<QuestionSet> questionSets = Arrays.asList(new QuestionSet("1"), new QuestionSet("2"));
        liveData.setValue(questionSets);

        when(service.getAllQuestionSets()).thenReturn(liveData);

        // Act
        presenter.loadAllQuestionSets();

        // Assert
        verify(service).getAllQuestionSets();
        verify(view).displayQuestionSets(questionSets);
    }

    @Test
    public void testAddNewQuestionSet() {

        String name = "New Set";
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onSuccess();
            return null;
        }).when(service).insert(any(QuestionSet.class), any(Callback.class));

        // Prepare LiveData for reloading question sets
        MutableLiveData<List<QuestionSet>> liveData = new MutableLiveData<>();
        when(service.getAllQuestionSets()).thenReturn(liveData);

        // Act
        presenter.addNewQuestionSet(name);

        // Assert
        verify(service).insert(any(QuestionSet.class), any(Callback.class));
        verify(view).showMessage("Question set added successfully.");
        verify(service).getAllQuestionSets(); // Once during setup, once after insertion
    }

    @Test
    public void testDeleteQuestionSet() {

        QuestionSet questionSet = new QuestionSet("1");
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onSuccess();
            return null;
        }).when(service).delete(any(QuestionSet.class), any(Callback.class));

        // Prepare LiveData for reloading question sets
        MutableLiveData<List<QuestionSet>> liveData = new MutableLiveData<>();
        when(service.getAllQuestionSets()).thenReturn(liveData);

        // Act
        presenter.deleteQuestionSet(questionSet);

        // Trigger reloading question sets
        liveData.setValue(Arrays.asList()); // Update the LiveData to trigger observer

        // Assert
        verify(service).delete(eq(questionSet), any(Callback.class));
        verify(view).showMessage("Question set deleted successfully.");
        verify(service).getAllQuestionSets(); // Once during setup, once after deletion
    }

    @Test
    public void testOnQuestionSetSelected() {
        // Arrange
        QuestionSet questionSet = new QuestionSet("1");

        // Act
        presenter.onQuestionSetSelected(questionSet);

        // Assert
        verify(view).navigateToQuestionSetDetails(questionSet);
    }

    @Test
    public void testOnStartExaminationSessionButtonClicked() {
        // Arrange
        QuestionSet questionSet = new QuestionSet("1");

        // Act
        presenter.onStartExaminationSessionButtonClicked(questionSet);

        // Assert
        verify(view).navigateToExaminationSession(questionSet);
    }
}