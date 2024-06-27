package project.study.app.presenter;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.service.interfaces.Callback;
import project.study.app.service.interfaces.QuestionSetService;
import project.study.app.service.interfaces.SingleItemCallback;
import project.study.app.view.interfaces.QuestionSetDetailsView;

public class QuestionSetDetailsPresenter {

    private final QuestionSetService service;
    private final QuestionSetDetailsView view;
    private QuestionSet currentQuestionSet;

    public QuestionSetDetailsPresenter(QuestionSetService service, QuestionSetDetailsView view) {

        this.service = service;
        this.view = view;
    }

    /**
     * Loads the question set (all questions) with the given name.
     *
     * @param questionSetName the name of the question set to load
     */
    public void loadQuestionSet(String questionSetName) {
        service.getQuestionSetByName(questionSetName, new SingleItemCallback<QuestionSet>() {
            @Override
            public void onSuccess(QuestionSet questionSet) {
                currentQuestionSet = questionSet;
                view.displayQuestions(questionSet.getQuestions());
            }

            @Override
            public void onError(Exception e) {
                view.showMessage("Error loading question set: " + e.getMessage());
            }
        });
    }

    /**
     * Adds a new question to the current question set.
     *
     * @param question the question to add
     */
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
