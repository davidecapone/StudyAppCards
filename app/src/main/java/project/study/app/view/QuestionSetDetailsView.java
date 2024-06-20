package project.study.app.view;

import java.util.List;

import project.study.app.model.domain.Question;

public interface QuestionSetDetailsView {
    void displayQuestions(List<Question> questions);

    void setQuestionSetName(String questionSetName);

    void showMessage(String s);
}
