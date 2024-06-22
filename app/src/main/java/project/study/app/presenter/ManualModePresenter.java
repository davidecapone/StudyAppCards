package project.study.app.presenter;

import androidx.lifecycle.LiveData;

import org.junit.Test;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.service.Callback;
import project.study.app.service.QuestionSetService;
import project.study.app.view.ManualModeView;

import java.util.List;

/**
 * Presenter for manual mode view
 */
public class ManualModePresenter {

    private final QuestionSetService service;
    private final ManualModeView view;

    public ManualModePresenter(QuestionSetService questionSetService, ManualModeView view) {

        this.service = questionSetService;
        this.view = view;
    }

    public void loadAllQuestionSets() {

        LiveData<List<QuestionSet>> questionSets = service.getAllQuestionSets();
        questionSets.observeForever(view::displayQuestionSets);
    }

    public void addNewQuestionSet(String name) {
        QuestionSet newQuestionSet = new QuestionSet(name);
        service.insert(newQuestionSet, new Callback() {
            @Override
            public void onSuccess() {
                view.showMessage("Question set added successfully.");
                loadAllQuestionSets(); // Refresh the list after successful insertion
            }

            @Override
            public void onError(Exception e) {
                view.showMessage("Error adding question set: " + e.getMessage());
            }
        });
    }

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

    public void onQuestionSetSelected(QuestionSet questionSet) {

        this.view.navigateToQuestionSetDetails(questionSet);
    }

    public void onStartExaminationSessionButtonClicked(QuestionSet questionSet) {
        view.navigateToExaminationSession(questionSet);
    }
}
