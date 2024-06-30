package project.study.app.view.interfaces;

/**
 * Interfact for the PomodoroModeView
 */
public interface PomodoroModeView {

    /**
     * Updates the timer
     * @param secondRemaining the seconds remaining
     */
    void updateTimer(int secondRemaining);

    /**
     * Shows the study session
     */
    void showStudySession();

    /**
     * Shows the break session
     */
    void showBreakSession();

    /**
     * Shows the insert questions session
     */
    void showInsertQuestionsSession();

    /**
     * Shows the examination session
     */
    void showExaminationSession();

    /**
     * Shows the completion message
     */
    void showCompletionMessage();
}
