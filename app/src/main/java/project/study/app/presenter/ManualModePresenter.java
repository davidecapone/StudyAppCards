package project.study.app.presenter;

import androidx.lifecycle.LiveData;
import project.study.app.model.domain.QuestionSet;
import project.study.app.service.interfaces.Callback;
import project.study.app.service.interfaces.Service;
import project.study.app.view.interfaces.ManualModeView;
import java.util.List;

/**
 * Presenter for manual mode view
 */
public class ManualModePresenter {
    private final Service service;
    private final ManualModeView view;

    /**
     * Constructor to create a new ManualModePresenter.
     *
     * @param service The service to fetch the question sets
     * @param view The view to display the question sets
     */
    public ManualModePresenter(Service service, ManualModeView view) {
        this.service = service;
        this.view = view;
    }
    /**
     * Loads all question sets from the database and displays them in the view.
     */
    public void loadAllQuestionSets() {
        LiveData<List<QuestionSet>> questionSets = service.getAllQuestionSets();
        questionSets.observeForever(view::displayQuestionSets);
    }
    /**
     * Adds a new question set to the database.
     *
     * @param name The name of the new question set
     */
    public void addNewQuestionSet(String name) {
        QuestionSet newQuestionSet = new QuestionSet(name);
        service.insert(newQuestionSet, new Callback() {
            /**
             * Callback method to handle the result of the insert operation.
             */
            @Override
            public void onSuccess() {
                view.showMessage("Question set added successfully.");
                loadAllQuestionSets(); // Refresh the list after successful insertion
            }
            /**
             * Callback method to handle errors during the insert operation.
             *
             * @param e The exception that occurred during the insert operation
             */
            @Override
            public void onError(Exception e) {
                view.showMessage("Error adding question set: " + e.getMessage());
            }
        });
    }
    /**
     * Deletes the specified question set from the database.
     *
     * @param questionSet The question set to delete
     */
    public void deleteQuestionSet(QuestionSet questionSet) {
        service.delete(questionSet, new Callback() {
            @Override
            public void onSuccess() {
                view.showMessage("Question set deleted successfully.");
                loadAllQuestionSets(); // Refresh the list after successful deletion
            }
            @Override
            public void onError(Exception e) {
                view.showMessage("Error deleting question set: " + e.getMessage());
            }
        });
    }
    /**
     * Handles the event when a question set is selected in the view.
     *
     * @param questionSet The selected question set
     */
    public void onQuestionSetSelected(QuestionSet questionSet) {
        this.view.navigateToQuestionSetDetails(questionSet);
    }
    /**
     * Handles the event when the start examination session button is clicked.
     *
     * @param questionSet The question set for the examination session
     */
    public void onStartExaminationSessionButtonClicked(QuestionSet questionSet) {
        view.navigateToExaminationSessionActivity(questionSet);
    }
}
