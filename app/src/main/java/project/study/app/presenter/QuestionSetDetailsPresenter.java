package project.study.app.presenter;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.service.Callback;
import project.study.app.service.QuestionSetService;
import project.study.app.service.SingleItemCallback;
import project.study.app.view.QuestionSetDetailsView;

public class QuestionSetDetailsPresenter {

    private final QuestionSetService service;
    private final QuestionSetDetailsView view;
    private QuestionSet currentQuestionSet;

    public QuestionSetDetailsPresenter(QuestionSetService service, QuestionSetDetailsView view) {

        this.service = service;
        this.view = view;
    }

    public void loadQuestions(String questionSetName) {
        service.getQuestionSetByName(questionSetName, new SingleItemCallback<QuestionSet>() {
            @Override
            public void onSuccess(QuestionSet questionSet) {
                currentQuestionSet = questionSet;
                view.setQuestionSetName(questionSet.getQuestionSetName());
                view.displayQuestions(questionSet.getQuestions());
            }

            @Override
            public void onError(Exception e) {
                view.showMessage("Error loading question set: " + e.getMessage());
            }
        });
    }

    public void addQuestion(Question question) {
        currentQuestionSet.addQuestion(question);
        service.update(currentQuestionSet, new Callback() {
            @Override
            public void onSuccess() {
                view.displayQuestions(currentQuestionSet.getQuestions());
                view.showMessage("Question added successfully.");
            }

            @Override
            public void onError(Exception e) {
                view.showMessage("Error adding question: " + e.getMessage());
            }
        });
    }

}
