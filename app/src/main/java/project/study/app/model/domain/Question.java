package project.study.app.model.domain;

public interface Question<A> {

    String getQuestionText();
    void setQuestionText(String newText);
    Boolean checkAnswer(A answer);
}
