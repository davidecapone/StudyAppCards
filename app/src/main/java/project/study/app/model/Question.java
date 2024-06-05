package project.study.app.model;

public interface Question<A> {

    String getQuestionText();
    void setQuestionText(String newText);
    Boolean checkAnswer(A answer);
}
