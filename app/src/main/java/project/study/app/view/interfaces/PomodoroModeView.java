package project.study.app.view.interfaces;

import project.study.app.presenter.PomodoroSessions;

/**
 * Interfact for the PomodoroModeView
 */
public interface PomodoroModeView {

    /**
     * Updates the timer
     * @param secondRemaining the seconds remaining
     */
    void updateTimer(int secondRemaining);

    void showSession(PomodoroSessions.Session session);

    void showCompletionMessage();
}
