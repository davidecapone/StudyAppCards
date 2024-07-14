package project.study.app.presenter;

import project.study.app.view.interfaces.PomodoroModeView;
import project.study.app.util.Timer;
import project.study.app.util.TimerCallback;

/**
 * The presenter will manage the different timers and notify the view of changes.
 */
public class PomodoroModePresenter extends PomodoroSessions implements TimerCallback {
    private final PomodoroModeView view;
    private final Timer timer;
    private Session currentSession;

    public PomodoroModePresenter(PomodoroModeView view, Timer timer) {
        this.view = view;
        this.timer = timer;
    }

    /**
     * Starts the Pomodoro mode by initiating the study session.
     * This method sets the current session to STUDY and begins the study timer.
     */
    public void startPomodoroMode() {
        startSession(Session.STUDY);
    }

    /**
     * Starts the specified session by updating the current session,
     * showing the appropriate session view, and starting the timer for the session's duration.
     *
     * @param session The session to start (STUDY, BREAK, INSERT_QUESTIONS, EXAMINATION, or COMPLETED).
     */
    private void startSession(Session session) {
        currentSession = session;
        view.showSession(session);
        startTimer(getSessionDuration(session));
    }

    /**
     * Gets the duration for the specified session.
     *
     * @param session The session for which the duration is required.
     * @return The duration of the session in milliseconds.
     */
    private long getSessionDuration(Session session) {
        switch (session) {
            case STUDY:
                return STUDY_DURATION;
            case BREAK:
                return BREAK_DURATION;
            case INSERT_QUESTIONS:
                return INSERT_QUESTIONS_DURATION;
            case EXAMINATION:
                return EXAMINATION_DURATION;
            case COMPLETED:
                return 0;
            default:
                throw new IllegalArgumentException("Unknown session: " + session);
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
                view.showCompletionMessage();
        }
    }

    /**
     * Starts a timer for the specified duration.
     * The timer will tick every second (1000 milliseconds) and invoke the TimerCallback methods.
     * @param duration The duration for which the timer should run, in milliseconds.
     */
    private void startTimer(long duration) {
        timer.start(duration, 1000L, this);
    }

    /**
     * Called at regular intervals as the timer counts down.
     * Updates the view with the remaining time in seconds.
     * @param millisUntilFinished The amount of time until finished in milliseconds.
     */
    @Override
    public void onTick(long millisUntilFinished) {
        int secondsRemaining = (int) (millisUntilFinished / 1000);
        view.updateTimer(secondsRemaining);
    }

    /**
     * Called when the timer finishes.
     * Moves to the next session in the Pomodoro cycle.
     */
    @Override
    public void onFinish() {
        moveToNextSession();
    }
}