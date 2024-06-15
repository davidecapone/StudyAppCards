package project.study.app.presenter;

import project.study.app.model.domain.QuestionSet;
import project.study.app.persistence.FakeRepository;

import java.util.List;

/**
 * Presenter for manual mode view
 */
public class ManualModePresenter {

    // (fake) repository
    private FakeRepository repository;

    public ManualModePresenter() {
        this.repository = new FakeRepository();
    }

    /**
     * Get all question sets in the repository
     * @return list of question sets
     */
    public List<QuestionSet> getAllQuestionSets() {
        return repository.getAllQuestionSets();
    }
}
