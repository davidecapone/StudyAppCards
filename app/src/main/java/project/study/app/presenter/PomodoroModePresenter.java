package project.study.app.presenter;

import project.study.app.view.interfaces.PomodoroModeView;
import project.study.app.util.Timer;
import project.study.app.util.TimerCallback;

/**
 * The presenter will manage the different timers and notify the view of changes.
 */
public class PomodoroModePresenter implements TimerCallback {

    // Study duration is 30 minutes
    private static final long STUDY_DURATION = 30 * 1000L;

    // Break duration is 10 minutes
    private static final long BREAK_DURATION = 10 * 1000L;

    // Insert questions duration is 10 minutes
    private static final long INSERT_QUESTIONS_DURATION = 10 * 1000L;

    // Examination duration is 30 minutes
    private static final long EXAMINATION_DURATION = 30 * 1000L;

    private final PomodoroModeView view;
    private final Timer timer;
    private Session currentSession;

    public enum Session {
        STUDY,
        BREAK,
        INSERT_QUESTIONS,
        EXAMINATION,
        COMPLETED
    }

    public PomodoroModePresenter(PomodoroModeView view, Timer timer) {
        this.view = view;
        this.timer = timer;
        this.currentSession = Session.COMPLETED;
    }

    /**
     * Starts the Pomodoro mode by initiating the study session.
     * This method sets the current session to STUDY and begins the study timer.
     */
    public void startPomodoroMode() {
        // Initiate the study session as the first session in the Pomodoro mode.
        startSession(Session.STUDY);
    }

    /**
     * Starts a timer for the specified duration.
     * The timer will tick every second (1000 milliseconds) and invoke the TimerCallback methods.
     *
     * @param duration The duration for which the timer should run, in milliseconds.
     */
    private void startTimer(long duration) {
        // Start the timer with the specified duration, ticking every 1000 milliseconds (1 second).
        // The current instance is passed as the TimerCallback.
        timer.start(duration, 1000L, this);
    }

    /**
     * Starts the specified session by updating the current session,
     * showing the appropriate session view, and starting the timer for the session's duration.
     *
     * @param session The session to start (STUDY, BREAK, INSERT_QUESTIONS, EXAMINATION, or COMPLETED).
     */
    private void startSession(Session session) {
        currentSession = session;
        switch (session) {
            case STUDY:
                view.showStudySession();
                startTimer(STUDY_DURATION);
                break;
            case BREAK:
                view.showBreakSession();
                startTimer(BREAK_DURATION);
                break;
            case INSERT_QUESTIONS:
                view.showInsertQuestionsSession();
                startTimer(INSERT_QUESTIONS_DURATION);
                break;
            case EXAMINATION:
                view.showExaminationSession();
                startTimer(EXAMINATION_DURATION);
                break;
            case COMPLETED:
                view.showCompletionMessage();
                break;
        }
    }

    /**
     * Moves to the next session based on the current session.
     * The order of sessions is: STUDY -> BREAK -> INSERT_QUESTIONS -> EXAMINATION -> COMPLETED.
     */
    private void moveToNextSession() {
        switch (currentSession) {
            case STUDY:
                startSession(Session.BREAK); // Move from STUDY to BREAK session
                break;
            case BREAK:
                startSession(Session.INSERT_QUESTIONS); // Move from BREAK to INSERT_QUESTIONS session
                break;
            case INSERT_QUESTIONS:
                startSession(Session.EXAMINATION); // Move from INSERT_QUESTIONS to EXAMINATION session
                break;
            case EXAMINATION:
                startSession(Session.COMPLETED); // Move from EXAMINATION to COMPLETED session
                break;
            case COMPLETED:
                break;
        }
    }

    /**
     * Called at regular intervals as the timer counts down.
     * Updates the view with the remaining time in seconds.
     *
     * @param millisUntilFinished The amount of time until finished in milliseconds.
     */
    @Override
    public void onTick(long millisUntilFinished) {
        // Convert the remaining time from milliseconds to seconds
        int secondsRemaining = (int) (millisUntilFinished / 1000);
        view.updateTimer(secondsRemaining);
    }

    /**
     * Called when the timer finishes.
     * Moves to the next session in the Pomodoro cycle.
     */
    @Override
    public void onFinish() {
        // Move to the next session in the Pomodoro cycle
        moveToNextSession();
    }
}