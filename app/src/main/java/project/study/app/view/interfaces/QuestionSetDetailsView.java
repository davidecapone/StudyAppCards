package project.study.app.view.interfaces;

import java.util.List;

import project.study.app.model.domain.Question;

public interface QuestionSetDetailsView {
    void displayQuestions(List<Question> questions);

    void showMessage(String s);

}
