package project.study.app.presenter;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.persistence.FakeRepository;
import project.study.app.view.ManualModeView;

import java.util.List;

/**
 * Presenter for manual mode view
 */
public class ManualModePresenter {

    // (fake) repository
    private final FakeRepository repository;

    // view
    private final ManualModeView view;

    public ManualModePresenter(FakeRepository repository, ManualModeView view) {
        this.repository = repository;
        this.view = view;
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
        try{
            return repository.searchQuestionSet(questionSetName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Navigate to question set view when the button to create a new question set is pressed
     */
    public void onCreateNewQuestionSetButtonPressed(){

        view.navigateToQuestionSetView(new QuestionSet("New Question Set"));
    }

    /**
     * Delete a question set from the repository
     * @param questionSetName name of the question set
     */
    public void deleteQuestionSet(String questionSetName) {
        repository.deleteQuestionSet(questionSetName);
    }

    /**
     * Navigate to question set view when a question set is selected
     * @param questionSet the question set to be updated
     */
    public void onQuestionSetButtonPressed(QuestionSet questionSet) {
        view.navigateToQuestionSetView(questionSet);
    }
}
