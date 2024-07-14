package project.study.app.presenter;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.service.interfaces.Callback;
import project.study.app.service.interfaces.Service;
import project.study.app.service.interfaces.SingleItemCallback;
import project.study.app.view.interfaces.QuestionSetDetailsView;

public class QuestionSetDetailsPresenter {
    private final Service service;
    private final QuestionSetDetailsView view;
    private QuestionSet currentQuestionSet;

    /**
     * Constructor to create a new QuestionSetDetailsPresenter.
     *
     * @param service The service to fetch the question set
     * @param view The view to display the question set details
     */
    public QuestionSetDetailsPresenter(Service service, QuestionSetDetailsView view) {
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
            /**
             * Callback method to handle the result of fetching the question set.
             *
             * @param questionSet The question set fetched from the service
             */
            @Override
            public void onSuccess(QuestionSet questionSet) {
                currentQuestionSet = questionSet;
                view.displayQuestions(questionSet.getQuestions());
            }
            /**
             * Callback method to handle an error fetching the question set.
             *
             * @param e The exception that occurred
             */
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
            /**
             * Callback method to handle the result of updating the question set.
             */
            @Override
            public void onSuccess() {
                view.displayQuestions(currentQuestionSet.getQuestions());
                view.showMessage("Question added successfully.");
            }
            /**
             * Callback method to handle an error updating the question set.
             *
             * @param e The exception that occurred
             */
            @Override
            public void onError(Exception e) {
                view.showMessage("Error adding question: " + e.getMessage());
            }
        });
    }
}
