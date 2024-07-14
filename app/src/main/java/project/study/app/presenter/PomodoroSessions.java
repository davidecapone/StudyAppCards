package project.study.app.presenter;

/**
 * Utility class containing session duration constants and the Session enum
 * for the Pomodoro mode.
 */
public class PomodoroSessions {

    protected static final long STUDY_DURATION = 30 * 1000L;
    protected static final long BREAK_DURATION = 10 * 1000L;
    protected static final long INSERT_QUESTIONS_DURATION = 10 * 1000L;
    protected static final long EXAMINATION_DURATION = 30 * 1000L;

    /**
     * Enum representing the different sessions in the Pomodoro mode.
     */
    public enum Session {
        STUDY,
        BREAK,
        INSERT_QUESTIONS,
        EXAMINATION,
        COMPLETED
    }
}
