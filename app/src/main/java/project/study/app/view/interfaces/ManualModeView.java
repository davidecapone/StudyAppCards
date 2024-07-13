package project.study.app.view.interfaces;

import java.util.List;
import project.study.app.model.domain.QuestionSet;

/**
 * Interface for the ManualModeView
 */
public interface ManualModeView {
    /**
     * Displays question sets
     *
     * @param questionSets the list of question sets
     */
    void displayQuestionSets(List<QuestionSet> questionSets);
    /**
     * Shows a message
     *
     * @param message the message
     */
    void showMessage(String message);
    /**
     * Navigates to the question set details view
     *
     * @param questionSet the question set to navigate to
     */
    void navigateToQuestionSetDetails(QuestionSet questionSet);
    /**
     * Navigates to the examination session view
     *
     * @param questionSet the question set on which to perform the examination
     */
    void navigateToExaminationSessionActivity(QuestionSet questionSet);
}
