package project.study.app;

public interface Question<AnswerType> {

    String getQuestionText();
    void setQuestionText(String newText);
    Boolean checkAnswer(AnswerType answer);
}
