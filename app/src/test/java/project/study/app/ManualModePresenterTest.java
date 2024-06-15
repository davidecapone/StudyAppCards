package project.study.app;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import project.study.app.model.domain.QuestionSet;
import project.study.app.persistence.FakeRepository;
import project.study.app.presenter.ManualModePresenter;

import static org.junit.Assert.assertEquals;

public class ManualModePresenterTest {

    private FakeRepository repository;

    private ManualModePresenter presenter;

    List<QuestionSet> questionSets;

    @Before
    public void setUp() {
        repository = new FakeRepository();

        presenter = new ManualModePresenter();
    }

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


}
