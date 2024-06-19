package project.study.app.presenter;

import androidx.lifecycle.LiveData;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.persistence.FakeRepository;
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
    
    public void addNewQuestionSet(QuestionSet newQuestionSet) {
        this.service.insert(newQuestionSet);
        view.showMessage("Question set added successfully.");
    }

    public void deleteQuestionSet(QuestionSet questionSet) {
        service.delete(questionSet);
        view.showMessage("Question set deleted successfully.");
    }
}
