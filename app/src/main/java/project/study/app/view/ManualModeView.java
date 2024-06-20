package project.study.app.view;

import java.util.List;

import project.study.app.model.domain.QuestionSet;

public interface ManualModeView {
    void displayQuestionSets(List<QuestionSet> questionSets);

    void showMessage(String message);

    void navigateToQuestionSetDetails(QuestionSet questionSet);
}
