package project.study.app.view.interfaces;

import project.study.app.model.domain.Question;

/**
 * Interface for the ExaminationSessionView
 */
public interface ExaminationSessionView {
    /**
     * Displays the question
     *
     * @param question the question
     */
    void displayQuestion(Question question);
    /**
     * Shows a message
     *
     * @param message the message
     */
    void showMessage(String message);
    /**
     * Shows whether the answer is correct
     */
    void showCorrectAnswerFeedback();
    /**
     * Shows whether the answer is not correct
     */
    void showIncorrectAnswerFeedback();
    /**
     * Navigates to the manual mode view
     */
    void navigateToManualModeActivity();
}
