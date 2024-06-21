package project.study.app;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import project.study.app.model.domain.QuestionSet;
import project.study.app.presenter.ManualModePresenter;
import project.study.app.service.QuestionSetService;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.view.ManualModeView;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;


/**
 * Test class for ManualModePresenter
 */
public class ManualModePresenterTest {

    @Rule // Rule to ensure LiveData executes synchronously in the test environment:
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private QuestionSetService service;
    private ManualModePresenter presenter;
    private ManualModeView view;

    @Before
    public void setUp() {
        // Create mock instances of the service and view:
        service = mock(QuestionSetServiceImplementation.class);
        view = mock(ManualModeView.class);

        // Initialize the presenter with the mocked service and view:
        presenter = new ManualModePresenter(service, view);
    }

    @Test
    public void testLoadAllQuestionSets() {

        // Prepare mock data and set up the expected behavior of the service
        List<QuestionSet> mockQuestionSets = Arrays.asList(
                new QuestionSet("Sample1"),
                new QuestionSet("Sample2")
        );
        MutableLiveData<List<QuestionSet>> liveData = new MutableLiveData<>();
        liveData.setValue(mockQuestionSets);

        when(service.getAllQuestionSets()).thenReturn(liveData);

        // Call the method to be tested on the presenter:
        presenter.loadAllQuestionSets();

        // Verify that the view's displayQuestionSets method is called with the correct data
        verify(view).displayQuestionSets(mockQuestionSets);
    }

    @Test
    public void testAddNewQuestionSet() {
        String name = "Sample";

        presenter.addNewQuestionSet(name);

        verify(service).insert(any(QuestionSet.class));
        verify(view).showMessage("Question set added successfully.");
    }

    @Test
    public void testDeleteQuestionSet() {

        // Prepare a QuestionSet object to be deleted
        QuestionSet questionSet = new QuestionSet("Sample");

        // Call the method to delete a question set on the presenter
        presenter.deleteQuestionSet(questionSet);

        // Verify that the service's delete method and the view's showMessage method are called
        verify(service).delete(questionSet);
        verify(view).showMessage("Question set deleted successfully.");
    }

    @Test
    public void testOnQuestionSetSelected() {

        QuestionSet questionSet = new QuestionSet("Sample");

        presenter.onQuestionSetSelected(questionSet);

        verify(view).navigateToQuestionSetDetails(questionSet);
    }

    @Test
    public void testStartExaminationSession() {
        // Arrange
        QuestionSet questionSet = new QuestionSet("Sample");

        presenter.onStartExaminationSessionButtonClicked(questionSet);

        // Assert
        verify(view).navigateToExaminationSession(questionSet);
    }
}