package project.study.app;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import project.study.app.model.domain.QuestionSet;
import project.study.app.persistence.FakeRepository;
import project.study.app.presenter.QuestionSetListPresenter;

import static org.junit.Assert.assertEquals;

/**
 * Test class for ManualModePresenter
 */
public class QuestionSetListPresenterTest {

    // (fake) repository
    private FakeRepository repository;

    // presenter
    private QuestionSetListPresenter presenter;

    // list of question sets
    List<QuestionSet> questionSets;

    @Before
    public void setUp() {
        repository = new FakeRepository();

        presenter = new QuestionSetListPresenter();
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

    @Test
    public void testAddQuestionSet() {

        // create a new question set
        QuestionSet newQuestionSet = new QuestionSet("New Question Set");

        // add the new question set to the repository
        repository.addQuestionSet(newQuestionSet);

        // set the repository in the presenter
        presenter.setRepository(repository);

        // add a new question set via the presenter
        presenter.addQuestionSet(newQuestionSet);

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
        assertEquals(null, questionSet);
    }
}