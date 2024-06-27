package project.study.app.view.interfaces;

public interface PomodoroModeView {
    void updateTimer(int secondRemaining);
    void showStudySession();
    void showBreakSession();
    void showInsertQuestionsSession();
    void showExaminationSession();
    void showCompletionMessage();
}
