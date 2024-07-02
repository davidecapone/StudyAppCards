package project.study.app.presenter;

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
import project.study.app.service.interfaces.Callback;
import project.study.app.service.interfaces.QuestionSetService;
import project.study.app.view.interfaces.ManualModeView;
import java.util.Arrays;
import java.util.List;

/**
 * Test class for the ManualModePresenter
 */
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

    /**
     * Test that the loadAllQuestionSets method calls the service to get all question sets
     */
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
    /**
     * Test that the addNewQuestionSet method calls the service to insert a new question set
     */
    @Test
    public void testAddNewQuestionSet() {
        // Arrange
        String name = "New Set";
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onSuccess();
            return null;
        }).when(service).insert(any(QuestionSet.class), any(Callback.class));
        MutableLiveData<List<QuestionSet>> liveData = new MutableLiveData<>();
        when(service.getAllQuestionSets()).thenReturn(liveData);
        // Act
        presenter.addNewQuestionSet(name);
        // Assert
        verify(service).insert(any(QuestionSet.class), any(Callback.class));
        verify(view).showMessage("Question set added successfully.");
        verify(service).getAllQuestionSets(); // Once during setup, once after insertion
    }
    /**
     * Test that the deleteQuestionSet method calls the service to delete a question set
     */
    @Test
    public void testDeleteQuestionSet() {
        // Arrange
        QuestionSet questionSet = new QuestionSet("1");
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(1);
            callback.onSuccess();
            return null;
        }).when(service).delete(any(QuestionSet.class), any(Callback.class));
        MutableLiveData<List<QuestionSet>> liveData = new MutableLiveData<>();
        when(service.getAllQuestionSets()).thenReturn(liveData);
        // Act
        presenter.deleteQuestionSet(questionSet);
        liveData.setValue(Arrays.asList()); // Update the LiveData to trigger observer
        // Assert
        verify(service).delete(eq(questionSet), any(Callback.class));
        verify(view).showMessage("Question set deleted successfully.");
        verify(service).getAllQuestionSets(); // Once during setup, once after deletion
    }
    /**
     * Test that the onQuestionSetSelected method navigates to the question set details view
     */
    @Test
    public void testOnQuestionSetSelected() {
        // Arrange
        QuestionSet questionSet = new QuestionSet("1");
        // Act
        presenter.onQuestionSetSelected(questionSet);
        // Assert
        verify(view).navigateToQuestionSetDetails(questionSet);
    }
    /**
     * Test that the onStartExaminationSessionButtonClicked method navigates to the examination session view
     */
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