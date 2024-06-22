package project.study.app.view;

import project.study.app.model.domain.Answer;
import project.study.app.model.domain.Question;

public interface ExaminationSessionView {
    void showQuestion(Question question);

    void confirmAnswer(Answer answer); // calls nextQuestion() in ExaminationSessionPresenter

    void navigateToManualModeView();
}
