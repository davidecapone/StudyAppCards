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

    /**
     * Add a new question set to the repository
     * @param newQuestionSet name of the question set
     */
    public void addQuestionSet(QuestionSet newQuestionSet) {
        repository.addQuestionSet(newQuestionSet);
    }

    /**
     * Search for a question set by name in the repository
     * @param questionSetName name of the question set
     * @return the question set if found, null otherwise
     */
    public QuestionSet searchQuestionSet(String questionSetName) {
        return repository.searchQuestionSet(questionSetName);
    }

    public void setRepository(FakeRepository repository) {
        this.repository = repository;
    }
}
