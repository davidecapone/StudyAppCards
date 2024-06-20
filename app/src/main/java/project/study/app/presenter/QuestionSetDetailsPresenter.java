package project.study.app.presenter;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.service.QuestionSetService;
import project.study.app.view.QuestionSetDetailsView;

public class QuestionSetDetailsPresenter {

    private final QuestionSetService service;
    private final QuestionSetDetailsView view;
    private QuestionSet currentQuestionSet;

    public QuestionSetDetailsPresenter(QuestionSetService service, QuestionSetDetailsView view) {

        this.service = service;
        this.view = view;
    }

    public void loadQuestionSet(String name) {

        currentQuestionSet = service.getQuestionSetByName(name);
        view.setQuestionSetName(currentQuestionSet.getQuestionSetName());
        view.displayQuestions(currentQuestionSet.getQuestions());
    }

    public void addQuestion(Question question) {

        currentQuestionSet.addQuestion(question);
        view.displayQuestions(currentQuestionSet.getQuestions());
    }

    public void saveQuestionSet(String newName) {

        service.update(currentQuestionSet);
        view.showMessage("Question set updated successfully.");
    }
}
