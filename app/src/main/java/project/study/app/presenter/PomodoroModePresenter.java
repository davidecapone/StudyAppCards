package project.study.app.presenter;

import project.study.app.view.interfaces.PomodoroModeView;

/**
 * The presenter will manage the different timers and notify the view of changes.
  */
public class PomodoroModePresenter {

    private final PomodoroModeView view;
    public enum Session {
        STUDY,
        BREAK,
        INSERT_QUESTIONS,
        EXAMINATION,
        COMPLETED
    }
    private Session currentSession;

    public PomodoroModePresenter(PomodoroModeView view) {
        this.view = view;
    }

    public void startPomodoroMode() {
        startStudySession();
    }

    private void startStudySession() {
        currentSession = Session.STUDY;
        view.showStudySession();
        moveToNextSession();
    }

    private void startBreakSession() {
        currentSession = Session.BREAK;
        view.showBreakSession();
        moveToNextSession();
    }

    private void startInsertQuestionsSession() {
        currentSession = Session.INSERT_QUESTIONS;
        view.showInsertQuestionsSession();
        moveToNextSession();
    }

    private void startExaminationSession() {
        currentSession = Session.EXAMINATION;
        view.showExaminationSession();
        moveToNextSession();
    }

    private void moveToNextSession() {
        switch (currentSession) {
            case STUDY:
                startBreakSession();
                break;
            case BREAK:
                startInsertQuestionsSession();
                break;
            case INSERT_QUESTIONS:
                startExaminationSession();
                break;
            case EXAMINATION:
                currentSession = Session.COMPLETED;
                view.showCompletionMessage();
                break;
            case COMPLETED:
                break;
        }
    }
}
