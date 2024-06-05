package project.study.app.model;

public interface Question<AnswerType> {

    String getQuestionText();
    void setQuestionText(String newText);
    Boolean checkAnswer(AnswerType answer);
}
