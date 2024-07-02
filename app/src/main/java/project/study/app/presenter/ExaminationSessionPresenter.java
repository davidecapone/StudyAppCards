package project.study.app.presenter;

import project.study.app.model.Statistics;
import project.study.app.model.domain.Question;
import project.study.app.service.interfaces.QuestionSetService;
import project.study.app.service.interfaces.SingleItemCallback;
import project.study.app.view.interfaces.ExaminationSessionView;
import project.study.app.model.domain.QuestionSet;
import java.util.List;

/**
 * Presenter class responsible for managing the examination session.
 * This class interacts with the view and the service to handle the examination logic,
 * including loading questions, validating answers, and displaying statistics.
 */
public class ExaminationSessionPresenter {
    // Service to fetch the question set
    private final QuestionSetService service;
    // View to display the questions and statistics
    private final ExaminationSessionView view;
    // Statistics object to track correct and incorrect answers
    private Statistics stats;
    // List of questions in the current question set
    private List<Question> questions;
    // Index of the current question being displayed
    private int currentQuestionIndex;
    // Error messages
    private static final String EMPTY_QUESTION_SET_MESSAGE = "This question set is empty!";
    private static final String ERROR_LOADING_QUESTION_SET_MESSAGE = "Error loading question set: ";

    /**
     * Constructor to create a new ExaminationSessionPresenter.
     *
     * @param service The service to fetch the question set.
     * @param view The view to display the questions and statistics.
     */
    public ExaminationSessionPresenter(QuestionSetService service, ExaminationSessionView view) {
        this.service = service;
        this.view = view;
    }
    /**
     * Starts the examination by loading the questions for the given question set name.
     *
     * @param questionSetName The name of the question set to load.
     */
    public void startExamination(String questionSetName) {
        service.getQuestionSetByName(questionSetName, new QuestionSetCallback());
    }
    /**
     * Callback class to handle the result of fetching the question set.
     */
    private class QuestionSetCallback implements SingleItemCallback<QuestionSet> {
        /**
         * Initializes the examination session with the questions from the question set.
         *
         * @param item The question set containing the questions.
         */
        @Override
        public void onSuccess(QuestionSet item) {
            questions = item.getQuestions();
            currentQuestionIndex = 0;
            stats = new Statistics();
            if (questions != null && !questions.isEmpty()) {
                view.displayQuestion(questions.get(currentQuestionIndex));
            } else {
                view.showMessage(EMPTY_QUESTION_SET_MESSAGE);
            }
        }
        /**
         * Displays an error message if the question set cannot be loaded.
         *
         * @param e The exception that occurred while loading the question set.
         */
        @Override
        public void onError(Exception e) {
            view.showMessage(ERROR_LOADING_QUESTION_SET_MESSAGE + e.getMessage());
        }
    }
    /**
     * Validates the provided answer against the correct answer of the current question.
     *
     * @param answer The answer provided by the user.
     */
    public void validateAnswer(String answer) {
        if (questions == null || questions.isEmpty()) {
            return;
        }
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (currentQuestion.getAnswer().getCorrectAnswer().equals(answer)) {
            view.showCorrectAnswerFeedback();
            stats.incrementCorrectAnswers();
        } else {
            view.showIncorrectAnswerFeedback();
            stats.incrementIncorrectAnswers();
        }
        advanceToNextQuestion();
    }
    /**
     * Advances to the next question in the list or shows the statistics if the examination is complete.
     */
    private void advanceToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            view.displayQuestion(questions.get(currentQuestionIndex));
        } else {
            showStatistics();
            view.navigateToManualMode();
        }
    }
    /**
     * Displays the statistics of the examination session.
     */
    private void showStatistics() {
        double proportionCorrect = stats.calculateProportionCorrect();
        view.showMessage("Correct answers: " + proportionCorrect + "%");
    }
}
