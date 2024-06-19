package project.study.app;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import project.study.app.model.domain.QuestionSet;
import project.study.app.persistence.FakeRepository;
import project.study.app.presenter.ManualModePresenter;
import project.study.app.view.ManualModeView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.verify;

import org.mockito.Mockito;

/**
 * Test class for ManualModePresenter
 */
public class ManualModePresenterTest {

    // (fake) repository
    private FakeRepository repository;

    // presenter
    private ManualModePresenter presenter;

    // (mock) view
    private ManualModeView view;

    // list of question sets
    List<QuestionSet> questionSets;

    @Before
    public void setUp() {
        repository = new FakeRepository();

        view = Mockito.mock(ManualModeView.class);

        presenter = new ManualModePresenter(repository, view);
    }

    /**
     * Test the getAllQuestionSets method
     */
    @Test
    public void testGetAllQuestionSets() {

        // get all question sets in the presenter
        questionSets = presenter.getAllQuestionSets();

        // get the expected question sets from the repository
        List<QuestionSet> expectedQuestionSets = repository.getAllQuestionSets();

        // assert the size is the same
        assertEquals(expectedQuestionSets.size(), questionSets.size());

        // assert all elements are the same
        for (int i = 0; i < expectedQuestionSets.size(); i++) {
            assertEquals(expectedQuestionSets.get(i).getQuestionSetName(), questionSets.get(i).getQuestionSetName());
        }
    }

    /**
     * Test the addition of a new question set
     * Opens the question set creation view
     */
    @Test
    public void testAddQuestionSet() {

        presenter.onCreateNewQuestionSetButtonPressed();

        verify(view).navigateToQuestionSetCreation();
    }

    /**
     * Test the searchQuestionSet method
     */
    @Test
    public void testSearchQuestionSet(){

        // search for a question set by name
        QuestionSet questionSet = presenter.searchQuestionSet("Question Set 1");

        // assert the correct question set is found
        assertEquals("Question Set 1", questionSet.getQuestionSetName());

        // search for a question set that does not exist
        questionSet = presenter.searchQuestionSet("Question Set 4");

        // assert the correct question set is not found
        assertNull(questionSet);
    }
}