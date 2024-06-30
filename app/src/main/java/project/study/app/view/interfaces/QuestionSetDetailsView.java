package project.study.app.view.interfaces;

import java.util.List;

import project.study.app.model.domain.Question;

/**
 * Interface for the QuestionSetDetailsView
 */
public interface QuestionSetDetailsView {

    /**
     * Displays the questions
     * @param questions the list of questions
     */
    void displayQuestions(List<Question> questions);

    /**
     * Shows a message
     * @param s the message
     */
    void showMessage(String s);

}
