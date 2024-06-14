package project.study.app.model.domain;

/**
 * A class to represent a question
 */
public class Question {

    private String questionText;
    private Answer<?> answer;

    public Question(String questionText, Answer<?> answer) {
        this.questionText = questionText;
        this.answer = answer;
    }

    public String getText() {
        return this.questionText;
    }

    public void setText(String newText) {
        this.questionText = newText;
    }

    public Answer<?> getAnswer() {
        return this.answer;
    }

    public void setAnswer(Answer<?> newAnswer) {
        this.answer = newAnswer;
    }

}
