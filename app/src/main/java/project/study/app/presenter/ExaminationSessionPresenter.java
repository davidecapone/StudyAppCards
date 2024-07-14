package project.study.app.presenter;

import project.study.app.model.Statistics;
import project.study.app.model.domain.Question;
import project.study.app.service.interfaces.Service;
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
    private final Service service;
    private final ExaminationSessionView view;
    private Statistics stats;
    private List<Question> questions;
    private int currentQuestionIndex;
    private static final String EMPTY_QUESTION_SET_MESSAGE = "This question set is empty!";
    private static final String ERROR_LOADING_QUESTION_SET_MESSAGE = "Error loading question set: ";

    public ExaminationSessionPresenter(Service service, ExaminationSessionView view) {
        this.service = service;
        this.view = view;
    }

    /**
     * Starts the examination by loading the questions for the given question set name.
     * @param questionSetName The name of the question set to load.
     */
    public void startExamination(String questionSetName) {
        service.getQuestionSetByName(questionSetName, new SingleItemCallback<QuestionSet>() {
            @Override
            public void onSuccess(QuestionSet item) {
                initializeExaminationSession(item);
            }

            @Override
            public void onError(Exception e) {
                view.showMessage(ERROR_LOADING_QUESTION_SET_MESSAGE + e.getMessage());
            }
        });
    }

    /**
     * Initializes the examination session with the provided question set.
     * @param item The QuestionSet to initialize the session with.
     */
    private void initializeExaminationSession(QuestionSet item) {
        questions = item.getQuestions();
        currentQuestionIndex = 0;
        stats = new Statistics();

        if (questions != null && !questions.isEmpty()) {
            view.displayQuestion(questions.get(currentQuestionIndex));
        } else {
            view.showMessage(EMPTY_QUESTION_SET_MESSAGE);
            view.navigateToManualModeActivity();
        }
    }

    private Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    /**
     * Validates the provided answer against the correct answer of the current question.
     * @param answer The answer provided by the user.
     */
    public void validateAnswer(String answer) {
        if (questions == null || questions.isEmpty())
            return;

        Question currentQuestion = getCurrentQuestion();
        processAnswer(currentQuestion, answer);
        advanceToNextQuestion();
    }

    /**
     * Processes the answer for the current question.
     * @param currentQuestion The current question being processed.
     * @param answer The answer provided by the user.
     */
    private void processAnswer(Question currentQuestion, String answer) {
        if (currentQuestion.getAnswer().getCorrectAnswer().equals(answer)) {
            handleCorrectAnswer();
        } else {
            handleIncorrectAnswer();
        }
    }

    /**
     * Handles the case when the user provides the correct answer.
     */
    private void handleCorrectAnswer() {
        view.showCorrectAnswerFeedback();
        stats.incrementCorrectAnswers();
    }

    /**
     * Handles the case when the user provides the incorrect answer.
     */
    private void handleIncorrectAnswer() {
        view.showIncorrectAnswerFeedback();
        stats.incrementIncorrectAnswers();
    }

    /**
     * Advances to the next question in the list.
     */
    private void advanceToNextQuestion() {
        currentQuestionIndex++;
        displayNextQuestion(currentQuestionIndex);
    }

    private void displayNextQuestion(int currentQuestionIndex) {
        if (currentQuestionIndex < questions.size()) {
            view.displayQuestion(questions.get(currentQuestionIndex));
        } else {
            displayStatistics();
            view.navigateToManualModeActivity();
        }
    }

    private void displayStatistics() {
        double proportionCorrect = stats.calculateProportionCorrect();
        view.showMessage("Correct answers: " + proportionCorrect + "%");
    }
}