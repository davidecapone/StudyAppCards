package project.study.app;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import project.study.app.model.domain.QuestionSet;
import project.study.app.persistence.FakeRepository;
import project.study.app.presenter.ManualModePresenter;

import static org.junit.Assert.assertEquals;

/**
 * Test class for ManualModePresenter
 */
public class ManualModePresenterTest {

    // (fake) repository
    private FakeRepository repository;

    // presenter
    private ManualModePresenter presenter;

    // list of question sets
    List<QuestionSet> questionSets;

    @Before
    public void setUp() {
        repository = new FakeRepository();

        presenter = new ManualModePresenter();
    }

    /**
     * Test the getAllQuestionSets method
     */
    @Test
    public void testGetAllQuestionSets() {
        questionSets = presenter.getAllQuestionSets();

        List<QuestionSet> expectedQuestionSets = repository.getAllQuestionSets();

        // assert the size is the same
        assertEquals(expectedQuestionSets.size(), questionSets.size());

        // assert all elements are the same
        for (int i = 0; i < expectedQuestionSets.size(); i++) {
            assertEquals(expectedQuestionSets.get(i).getQuestionSetName(), questionSets.get(i).getQuestionSetName());
        }
    }

    @Test
    public void testAddQuestionSet() {

        // add the new question set to the repository
        repository.addQuestionSet("New Question Set");

        // set the repository in the presenter
        presenter.setRepository(repository);

        // add a new question set via the presenter
        presenter.addQuestionSet("New Question Set");

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

}