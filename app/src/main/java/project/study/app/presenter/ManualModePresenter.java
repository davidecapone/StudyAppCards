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

    public ManualModePresenter(Service service, ManualModeView view) {
        this.service = service;
        this.view = view;
    }

    /**
     * Loads all question sets from the database and displays them in the view.
     * Observes the data source and updates the view accordingly.
     */
    public void loadAllQuestionSets() {
        LiveData<List<QuestionSet>> questionSets = service.getAllQuestionSets();
        questionSets.observeForever(view::displayQuestionSets);
    }

    public void addNewQuestionSet(String name) {
        QuestionSet newQuestionSet = new QuestionSet(name);
        service.insert(newQuestionSet, new Callback() {
            @Override
            public void onSuccess() {
                handleSuccess("Question set added successfully.");
            }
            @Override
            public void onError(Exception e) {
                handleError("Error adding question set: " + e.getMessage());
            }
        });
    }

    public void deleteQuestionSet(QuestionSet questionSet) {
        service.delete(questionSet, new Callback() {
            @Override
            public void onSuccess() {
                handleSuccess("Question set deleted successfully.");
            }
            @Override
            public void onError(Exception e) {
                handleError("Error deleting question set: " + e.getMessage());
            }
        });
    }

    /**
     * Handles the event when a question set is selected in the view.
     * @param questionSet The selected question set
     */
    public void onQuestionSetSelected(QuestionSet questionSet) {
        view.navigateToQuestionSetDetails(questionSet);
    }

    /**
     * Handles the event when the start examination session is clicked.
     * @param questionSet The question set for the examination session
     */
    public void onStartExaminationSessionButtonClicked(QuestionSet questionSet) {
        view.navigateToExaminationSessionActivity(questionSet);
    }

    /**
     * Handles successful operations by showing a message and reloading all question sets.
     * @param message The success message to show.
     */
    private void handleSuccess(String message) {
        view.showMessage(message);
        loadAllQuestionSets();
    }

    /**
     * Handles error operations by showing an error message.
     * @param message The error message to show.
     */
    private void handleError(String message) {
        view.showMessage(message);

    }
}
