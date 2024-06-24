package project.study.app.view;

import project.study.app.model.domain.Answer;
import project.study.app.model.domain.Question;

public interface ExaminationSessionView {
    void displayQuestion(Question question);

    void showMessage(String message);

    void showCorrectAnswerFeedback();

    void showIncorrectAnswerFeedback();
}
